package net.thomilist.dimensionalinventories.module.builtin.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.NonNullList;
import net.thomilist.dimensionalinventories.compatibility.Compat;
import net.thomilist.dimensionalinventories.module.base.player.PlayerModuleState;
import net.thomilist.dimensionalinventories.util.ItemStackListHelper;

import java.lang.reflect.Type;

public class InventoryModuleState
    implements PlayerModuleState
{
    private static final int ARMOR_SIZE = Inventory.ALL_ARMOR_SLOTS.length;
    private static final int MAIN_SIZE = Inventory.INVENTORY_SIZE;
    private static final int OFF_HAND_SIZE = 1;
    private static final int ENDER_CHEST_SIZE = new PlayerEnderChestContainer().getContainerSize();

    public final NonNullList<ItemStack> armor = NonNullList.withSize(
        InventoryModuleState.ARMOR_SIZE,
        ItemStack.EMPTY
    );
    public final NonNullList<ItemStack> main = NonNullList.withSize(
        InventoryModuleState.MAIN_SIZE,
        ItemStack.EMPTY
    );
    public final NonNullList<ItemStack> offHand = NonNullList.withSize(
        InventoryModuleState.OFF_HAND_SIZE,
        ItemStack.EMPTY
    );
    public final NonNullList<ItemStack> enderChest = NonNullList.withSize(
        InventoryModuleState.ENDER_CHEST_SIZE,
        ItemStack.EMPTY
    );

    public InventoryModuleState()
    { }

    public InventoryModuleState( final ServerPlayer player )
    {
        this.loadFromPlayer( player );
    }

    @Override
    public Type type()
    {
        return InventoryModuleState.class;
    }

    @Override
    public void applyToPlayer( final ServerPlayer player )
    {
        ItemStackListHelper.assignItemStacks( this.armor, player.getInventory().armor );
        ItemStackListHelper.assignItemStacks( this.main, player.getInventory().items );
        ItemStackListHelper.assignItemStacks( this.offHand, player.getInventory().offhand );
        ItemStackListHelper.assignItemStacks( this.enderChest, Compat.SIMPLE_INVENTORY.getHeldStacks(player.getEnderChestInventory()) );
    }

    @Override
    public void loadFromPlayer( final ServerPlayer player )
    {
        ItemStackListHelper.assignItemStacks( player.getInventory().armor, this.armor );
        ItemStackListHelper.assignItemStacks( player.getInventory().items, this.main );
        ItemStackListHelper.assignItemStacks( player.getInventory().offhand, this.offHand );
        ItemStackListHelper.assignItemStacks( Compat.SIMPLE_INVENTORY.getHeldStacks(player.getEnderChestInventory()), this.enderChest );
    }

    public NonNullList<ItemStack> section( final InventorySection label )
    {
        return switch ( label )
        {
            case ARMOR -> this.armor;
            case MAIN -> this.main;
            case OFF_HAND -> this.offHand;
            case ENDER_CHEST -> this.enderChest;
        };
    }
}
