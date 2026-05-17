package net.thomilist.dimensionalinventories.module.builtin.curios;

import com.google.gson.Gson;
import net.minecraft.server.level.ServerPlayer;
import net.thomilist.dimensionalinventories.module.base.JsonModule;
import net.thomilist.dimensionalinventories.module.base.ModuleBase;
import net.thomilist.dimensionalinventories.module.base.player.JsonPlayerModule;
import net.thomilist.dimensionalinventories.module.version.StorageVersion;

public final class CuriosModule
    extends ModuleBase
    implements JsonPlayerModule<CuriosModuleState>
{
    private static final String MODULE_ID = "curios";
    private static final String DESCRIPTION = "Items in Curios accessory slots.";

    private static final StorageVersion[] STORAGE_VERSIONS = {
        StorageVersion.V2
    };

    private static final Gson GSON = JsonModule.GSON_BUILDER
        .registerTypeAdapter( CuriosModuleState.class, new CuriosModuleStateSerializerPair() )
        .create();

    private final CuriosModuleState state = new CuriosModuleState();

    public CuriosModule( final String groupId )
    {
        super( CuriosModule.STORAGE_VERSIONS, groupId, CuriosModule.MODULE_ID, CuriosModule.DESCRIPTION );
    }

    @Override
    public CuriosModuleState newInstance( final ServerPlayer player )
    {
        return new CuriosModuleState( player );
    }

    @Override
    public CuriosModuleState state()
    {
        return this.state;
    }

    @Override
    public CuriosModuleState defaultState()
    {
        return new CuriosModuleState();
    }

    @Override
    public Gson gson()
    {
        return CuriosModule.GSON;
    }
}
