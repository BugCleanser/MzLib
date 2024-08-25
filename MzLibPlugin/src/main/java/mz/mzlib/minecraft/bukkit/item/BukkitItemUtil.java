package mz.mzlib.minecraft.bukkit.item;

import mz.mzlib.minecraft.item.ItemStack;

public class BukkitItemUtil
{
    public static ItemStack fromBukkit(org.bukkit.inventory.ItemStack is)
    {
        if(CraftItemStack.create(null).staticGetWrappedClass().isAssignableFrom(is.getClass()))
            return CraftItemStack.create(is).getHandle();
        else
            return CraftItemStack.newInstance(is).getHandle();
    }
    public static org.bukkit.inventory.ItemStack toBukkit(ItemStack is)
    {
        return CraftItemStack.newInstance(is).getWrapped();
    }
}
