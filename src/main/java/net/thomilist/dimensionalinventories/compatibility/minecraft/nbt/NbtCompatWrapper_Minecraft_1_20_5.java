package net.thomilist.dimensionalinventories.compatibility.minecraft.nbt;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.MinecraftServer;
import net.thomilist.dimensionalinventories.compatibility.LimitedCompatibility;

@LimitedCompatibility( target = "Minecraft",
                       versions = ">=1.20.5" )
public final class NbtCompatWrapper_Minecraft_1_20_5
    implements NbtCompatWrapper
{
    private HolderLookup.Provider wrapperLookup;

    @Override
    public void onServerStarted( final MinecraftServer server )
    {
        NbtCompatWrapper.super.onServerStarted( server );
        this.wrapperLookup = server.registryAccess();
    }

    @Override
    public ItemStack toItemStack( final CompoundTag nbtCompound )
    {
        if ( nbtCompound.isEmpty() || nbtCompound.getString( "id" ).matches( "^minecraft:air$" ) )
        {
            return ItemStack.EMPTY;
        }

        return ItemStack.parseOptional( this.wrapperLookup, nbtCompound );
    }

    @Override
    public CompoundTag fromItemStack( final ItemStack itemStack )
    {
        if ( itemStack.isEmpty() )
        {
            return null;
        }

        return (CompoundTag) itemStack.save( this.wrapperLookup );
    }

    @Override
    public MobEffectInstance toMobEffectInstance( final CompoundTag nbtCompound )
    {
        return MobEffectInstance.load( nbtCompound );
    }

    @Override
    public CompoundTag fromMobEffectInstance( final MobEffectInstance statusEffectInstance )
    {
        return (CompoundTag) statusEffectInstance.save();
    }
}
