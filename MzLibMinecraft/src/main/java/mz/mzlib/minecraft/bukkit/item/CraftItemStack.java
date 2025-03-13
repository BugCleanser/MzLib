package mz.mzlib.minecraft.bukkit.item;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.wrapper.*;

@BukkitEnabled
@WrapCraftbukkitClass(@VersionName(name="OBC.inventory.CraftItemStack"))
public interface CraftItemStack extends WrapperObject
{
    WrapperFactory<CraftItemStack> FACTORY = WrapperFactory.find(CraftItemStack.class);
    @Deprecated
    @WrapperCreator
    static CraftItemStack create(Object wrapped)
    {
        return WrapperObject.create(CraftItemStack.class, wrapped);
    }
    @Override
    org.bukkit.inventory.ItemStack getWrapped();
    
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
