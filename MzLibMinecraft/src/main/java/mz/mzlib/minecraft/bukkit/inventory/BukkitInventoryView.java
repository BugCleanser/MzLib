package mz.mzlib.minecraft.bukkit.inventory;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapClassForName("org.bukkit.inventory.InventoryView")
public interface BukkitInventoryView extends WrapperObject
{
    WrapperFactory<BukkitInventoryView> FACTORY = WrapperFactory.of(BukkitInventoryView.class);
    @Deprecated
    @WrapperCreator
    static BukkitInventoryView create(Object wrapped)
    {
        return WrapperObject.create(BukkitInventoryView.class, wrapped);
    }
}
