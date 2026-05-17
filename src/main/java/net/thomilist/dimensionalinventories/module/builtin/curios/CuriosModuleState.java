package net.thomilist.dimensionalinventories.module.builtin.curios;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.thomilist.dimensionalinventories.module.base.player.PlayerModuleState;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CuriosModuleState
    implements PlayerModuleState
{
    public final Map<String, CompoundTag> slots = new HashMap<>();

    public CuriosModuleState()
    { }

    public CuriosModuleState( final ServerPlayer player )
    {
        this.loadFromPlayer( player );
    }

    @Override
    public Type type()
    {
        return CuriosModuleState.class;
    }

    @Override
    public void applyToPlayer( final ServerPlayer player )
    {
        CuriosApi
            .getCuriosInventory( player )
            .ifPresent( curios -> curios.getCurios().forEach( ( identifier, handler ) -> {
                final CompoundTag savedSlot = this.slots.get( identifier );

                if ( savedSlot == null )
                {
                    CuriosModuleState.clear( handler );
                }
                else
                {
                    handler.deserializeNBT( savedSlot.copy() );
                }
            } ) );
    }

    @Override
    public void loadFromPlayer( final ServerPlayer player )
    {
        this.slots.clear();

        CuriosApi
            .getCuriosInventory( player )
            .ifPresent( curios -> curios.getCurios().forEach( ( identifier, handler ) -> this.slots.put(
                identifier,
                handler.serializeNBT().copy()
            ) ) );
    }

    private static void clear( final ICurioStacksHandler handler )
    {
        CuriosModuleState.clear( handler.getStacks() );
        CuriosModuleState.clear( handler.getCosmeticStacks() );
    }

    private static void clear( final IDynamicStackHandler stacks )
    {
        for ( int slot = 0; slot < stacks.getSlots(); slot++ )
        {
            stacks.setStackInSlot( slot, ItemStack.EMPTY );
            stacks.setPreviousStackInSlot( slot, ItemStack.EMPTY );
        }
    }
}
