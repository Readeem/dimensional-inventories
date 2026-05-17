package net.thomilist.dimensionalinventories.module.builtin.shoulderentity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.thomilist.dimensionalinventories.mixin.PlayerEntityAccessor;
import net.thomilist.dimensionalinventories.module.base.player.PlayerModuleState;

import java.lang.reflect.Type;

public class ShoulderEntityModuleState
    implements PlayerModuleState
{
    public static EntityDataAccessor<CompoundTag> LEFT_SHOULDER_ENTITY = PlayerEntityAccessor.getLeftShoulderEntity();
    public static EntityDataAccessor<CompoundTag> RIGHT_SHOULDER_ENTITY = PlayerEntityAccessor.getRightShoulderEntity();

    public CompoundTag leftShoulderEntity = new CompoundTag();
    public CompoundTag rightShoulderEntity = new CompoundTag();
    public long shoulderEntityAddedTime = 0;

    public ShoulderEntityModuleState()
    { }

    public ShoulderEntityModuleState( final ServerPlayer player )
    {
        this.loadFromPlayer( player );
    }

    @Override
    public void applyToPlayer( final ServerPlayer player )
    {
        player.getEntityData().set( ShoulderEntityModuleState.LEFT_SHOULDER_ENTITY, this.leftShoulderEntity );
        player.getEntityData().set( ShoulderEntityModuleState.RIGHT_SHOULDER_ENTITY, this.rightShoulderEntity );
        ((PlayerEntityAccessor) player).setShoulderEntityAddedTime( this.shoulderEntityAddedTime );
    }

    @Override
    public void loadFromPlayer( final ServerPlayer player )
    {
        this.leftShoulderEntity = player.getShoulderEntityLeft();
        this.rightShoulderEntity = player.getShoulderEntityRight();
        this.shoulderEntityAddedTime = ((PlayerEntityAccessor) player).getShoulderEntityAddedTime();
    }

    @Override
    public Type type()
    {
        return ShoulderEntityModuleState.class;
    }
}
