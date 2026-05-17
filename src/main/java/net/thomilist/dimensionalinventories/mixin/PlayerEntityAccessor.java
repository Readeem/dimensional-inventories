package net.thomilist.dimensionalinventories.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin( Player.class )
public interface PlayerEntityAccessor
{
    @Accessor( "timeEntitySatOnShoulder" )
    long getShoulderEntityAddedTime();

    @Accessor( "timeEntitySatOnShoulder" )
    void setShoulderEntityAddedTime( long shoulderEntityAddedTime );

    @Invoker( "removeEntitiesOnShoulder" )
    void invokeDropShoulderEntities();

    @Accessor( "DATA_SHOULDER_LEFT" )
    static EntityDataAccessor<CompoundTag> getLeftShoulderEntity()
    {
        throw new AssertionError();
    }

    @Accessor( "DATA_SHOULDER_RIGHT" )
    static EntityDataAccessor<CompoundTag> getRightShoulderEntity()
    {
        throw new AssertionError();
    }
}
