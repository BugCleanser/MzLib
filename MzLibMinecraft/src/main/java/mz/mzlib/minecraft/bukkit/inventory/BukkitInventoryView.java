package mz.mzlib.minecraft.bukkit.inventory;

import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@BukkitOnly
@WrapClassForName("org.bukkit.inventory.InventoryView")
public interface BukkitInventoryView extends WrapperObject
{
    @WrapperCreator
    static BukkitInventoryView create(Object wrapped)
    {
        return WrapperObject.create(BukkitInventoryView.class, wrapped);
    }
}
