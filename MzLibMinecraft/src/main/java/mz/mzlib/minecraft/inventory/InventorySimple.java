package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.inventory.SimpleInventory", end=1400), @VersionName(name="net.minecraft.inventory.BasicInventory", begin=1400, end=1600), @VersionName(name="net.minecraft.inventory.SimpleInventory", begin=1600)})
public interface InventorySimple extends WrapperObject, Inventory
{
    @WrapperCreator
    static InventorySimple create(Object wrapped)
    {
        return WrapperObject.create(InventorySimple.class, wrapped);
    }
    
    @WrapConstructor
    InventorySimple staticNewInstance(int size);
    
    static InventorySimple newInstance(int size)
    {
        return create(null).staticNewInstance(size);
    }
}
