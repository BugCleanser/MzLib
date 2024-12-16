package mz.mzlib.minecraft.bukkit.item;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapFieldAccessor;

@WrapCraftbukkitClass(@VersionName(name="OBC.inventory.CraftItemStack"))
public interface CraftItemStack extends WrapperObject
{
    @Override
    org.bukkit.inventory.ItemStack getWrapped();
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static CraftItemStack create(org.bukkit.inventory.ItemStack wrapped)
    {
        return WrapperObject.create(CraftItemStack.class, wrapped);
    }

    @WrapConstructor
    CraftItemStack staticNewInstance(ItemStack handle);
    static CraftItemStack newInstance(ItemStack handle)
    {
        return create(null).staticNewInstance(handle);
    }
    @WrapConstructor
    CraftItemStack staticNewInstance(org.bukkit.inventory.ItemStack is);
    static CraftItemStack newInstance(org.bukkit.inventory.ItemStack is)
    {
        return create(null).staticNewInstance(is);
    }

    @WrapFieldAccessor("handle")
    ItemStack getHandle();
}
