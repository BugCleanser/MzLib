package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.SpecificImpl;
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
    
    static InventorySimple newInstance(int size)
    {
        return create(null).staticNewInstance(size);
    }
    
    InventorySimple staticNewInstance(int size);
    @VersionRange(end=1300)
    @WrapConstructor
    InventorySimple staticNewInstanceV_1300(String name, boolean hasCustomName, int size);
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1300)
    default InventorySimple staticNewInstanceV_1300(int size)
    {
        return this.staticNewInstanceV_1300(null, false, size);
    }
    @VersionRange(begin=1300, end=1400)
    @WrapConstructor
    InventorySimple staticNewInstanceV1300_1400(Text name, int size);
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1300, end=1400)
    default InventorySimple staticNewInstanceV1300_1400(int size)
    {
        return this.staticNewInstanceV1300_1400(Text.create(null), size);
    }
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1400)
    @WrapConstructor
    InventorySimple staticNewInstanceV1400(int size);
}
