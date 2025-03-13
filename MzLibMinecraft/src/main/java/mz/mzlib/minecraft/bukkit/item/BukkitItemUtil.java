package mz.mzlib.minecraft.bukkit.item;

import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.wrapper.WrapperObject;

public class BukkitItemUtil
{
    public static ItemStack fromBukkit(org.bukkit.inventory.ItemStack is)
    {
        if(WrapperObject.FACTORY.create(is).isInstanceOf(CraftItemStack.FACTORY))
            return CraftItemStack.FACTORY.create(is).getHandle();
        else
            return CraftItemStack.newInstance(is).getHandle();
    }
    public static org.bukkit.inventory.ItemStack toBukkit(ItemStack is)
    {
        return CraftItemStack.newInstance(is).getWrapped();
    }
}
