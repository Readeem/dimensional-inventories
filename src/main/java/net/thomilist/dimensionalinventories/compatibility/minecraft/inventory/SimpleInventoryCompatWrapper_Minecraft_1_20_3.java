package net.thomilist.dimensionalinventories.compatibility.minecraft.inventory;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.thomilist.dimensionalinventories.compatibility.LimitedCompatibility;

@LimitedCompatibility( target = "Minecraft",
                       versions = ">=1.20.3" )
public class SimpleInventoryCompatWrapper_Minecraft_1_20_3
    implements SimpleInventoryCompatWrapper
{
    @Override
    public NonNullList<ItemStack> getHeldStacks( final SimpleContainer simpleInventory )
    {
        return simpleInventory.getItems();
    }
}
