package mz.mzlib.minecraft.bukkit.inventory;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapClassForName("org.bukkit.inventory.Inventory")
public interface BukkitInventory extends WrapperObject
{
    WrapperFactory<BukkitInventory> FACTORY = WrapperFactory.of(BukkitInventory.class);
}
