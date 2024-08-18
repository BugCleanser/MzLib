package mz.mzlib.mc.bukkit.item;

import mz.mzlib.mc.item.ItemStack;

public class BukkitItemUtil
{
    public static ItemStack fromBukkit(org.bukkit.inventory.ItemStack is)
    {
        if(CraftItemStack.create(null).getDelegateClass().isAssignableFrom(is.getClass()))
            return CraftItemStack.create(is).getHandle();
        else
            return CraftItemStack.newInstance(is).getHandle();
    }
    public static org.bukkit.inventory.ItemStack toBukkit(ItemStack is)
    {
        return CraftItemStack.newInstance(is).getDelegate();
    }
}
