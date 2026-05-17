package net.thomilist.dimensionalinventories.module.builtin.curios;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import net.minecraft.nbt.CompoundTag;
import net.thomilist.dimensionalinventories.lostandfound.LostAndFound;
import net.thomilist.dimensionalinventories.lostandfound.LostAndFoundScope;
import net.thomilist.dimensionalinventories.util.gson.SerializerPair;

import java.lang.reflect.Type;

public class CuriosModuleStateSerializerPair
    implements SerializerPair<CuriosModuleState>
{
    @Override
    public CuriosModuleState fromJson( final JsonElement json,
                                       final Type typeOfT,
                                       final JsonDeserializationContext context )
        throws JsonParseException
    {
        if ( !json.isJsonObject() )
        {
            LostAndFound.log( "Unexpected JSON structure for Curios data (expected an object)", json.toString() );

            return new CuriosModuleState();
        }

        final CuriosModuleState state = new CuriosModuleState();
        final JsonObject slotJson = json.getAsJsonObject();

        for ( final String identifier : slotJson.keySet() )
        {
            try ( final LostAndFoundScope LAF = LostAndFound.push( identifier ) )
            {
                final CompoundTag tag = context.deserialize( slotJson.get( identifier ), CompoundTag.class );

                if ( tag != null )
                {
                    state.slots.put( identifier, tag );
                }
            }
        }

        return state;
    }

    @Override
    public JsonElement toJson( final CuriosModuleState src,
                               final Type typeOfSrc,
                               final JsonSerializationContext context )
    {
        final JsonObject json = new JsonObject();

        src.slots.forEach( ( identifier, tag ) -> {
            try ( final LostAndFoundScope LAF = LostAndFound.push( identifier ) )
            {
                json.add( identifier, context.serialize( tag, CompoundTag.class ) );
            }
        } );

        return json;
    }
}
