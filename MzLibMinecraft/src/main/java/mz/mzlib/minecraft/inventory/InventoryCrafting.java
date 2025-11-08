package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.inventory.CraftingInventory"))
public interface InventoryCrafting extends Inventory
{
    WrapperFactory<InventoryCrafting> FACTORY = WrapperFactory.of(InventoryCrafting.class);
}
