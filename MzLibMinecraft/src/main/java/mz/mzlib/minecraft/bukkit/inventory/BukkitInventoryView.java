package mz.mzlib.minecraft.bukkit.inventory;

import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@BukkitEnabled
@WrapClassForName("org.bukkit.inventory.InventoryView")
public interface BukkitInventoryView extends WrapperObject
{
    @WrapperCreator
    static BukkitInventoryView create(Object wrapped)
    {
        return WrapperObject.create(BukkitInventoryView.class, wrapped);
    }
}
