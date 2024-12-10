package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.inventory.SimpleInventory", end=1400), @VersionName(name="net.minecraft.inventory.BasicInventory", begin=1400, end=1600), @VersionName(name="net.minecraft.inventory.SimpleInventory", begin=1600)})
public interface InventoryCustom extends WrapperObject, Inventory
{
    @WrapperCreator
    static InventoryCustom create(Object wrapped)
    {
        return WrapperObject.create(InventoryCustom.class, wrapped);
    }
    
    @WrapConstructor
    InventoryCustom staticNewInstance(int size);
    static InventoryCustom newInstance(int size)
    {
        return create(null).staticNewInstance(size);
    }
}
