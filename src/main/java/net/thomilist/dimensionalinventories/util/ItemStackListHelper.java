package net.thomilist.dimensionalinventories.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;

public final class ItemStackListHelper
{
    private ItemStackListHelper()
    { }

    public static void assignItemStacks( final NonNullList<ItemStack> source, final NonNullList<ItemStack> target )
    {
        if ( source.size() != target.size() )
        {
            return;
        }

        for ( int i = 0; i < source.size(); i++ )
        {
            target.set( i, source.get( i ) );
        }
    }
}
