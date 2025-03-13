package mz.mzlib.minecraft.bukkit.inventory;

import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@BukkitEnabled
@WrapClassForName("org.bukkit.inventory.InventoryView")
public interface BukkitInventoryView extends WrapperObject
{
    WrapperFactory<BukkitInventoryView> FACTORY = WrapperFactory.find(BukkitInventoryView.class);
    @Deprecated
    @WrapperCreator
    static BukkitInventoryView create(Object wrapped)
    {
        return WrapperObject.create(BukkitInventoryView.class, wrapped);
    }
}
