package mz.mzlib.minecraft.bukkit.inventory;

import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@BukkitOnly
@WrapClassForName("org.bukkit.inventory.Inventory")
public interface BukkitInventory extends WrapperObject
{
    @WrapperCreator
    static BukkitInventory create(Object wrapped)
    {
        return WrapperObject.create(BukkitInventory.class, wrapped);
    }
}
