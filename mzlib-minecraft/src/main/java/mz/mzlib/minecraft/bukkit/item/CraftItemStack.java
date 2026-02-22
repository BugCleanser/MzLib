package mz.mzlib.minecraft.bukkit.item;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapCraftbukkitClass(@VersionName(name = "OBC.inventory.CraftItemStack"))
public interface CraftItemStack extends WrapperObject
{
    WrapperFactory<CraftItemStack> FACTORY = WrapperFactory.of(CraftItemStack.class);
    @Override
    org.bukkit.inventory.ItemStack getWrapped();

    @WrapConstructor
    CraftItemStack static$newInstance(ItemStack handle);
    static CraftItemStack newInstance(ItemStack handle)
    {
        return FACTORY.getStatic().static$newInstance(handle);
    }
    @WrapConstructor
    CraftItemStack static$newInstance(org.bukkit.inventory.ItemStack is);
    static CraftItemStack newInstance(org.bukkit.inventory.ItemStack is)
    {
        return FACTORY.getStatic().static$newInstance(is);
    }

    @WrapFieldAccessor("handle")
    ItemStack getHandle();
}

