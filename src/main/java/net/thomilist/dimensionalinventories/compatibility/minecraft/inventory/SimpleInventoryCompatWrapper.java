package net.thomilist.dimensionalinventories.compatibility.minecraft.inventory;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.thomilist.dimensionalinventories.compatibility.CompatWrapper;

public interface SimpleInventoryCompatWrapper
    extends CompatWrapper
{
    NonNullList<ItemStack> getHeldStacks( SimpleContainer simpleInventory );
}
