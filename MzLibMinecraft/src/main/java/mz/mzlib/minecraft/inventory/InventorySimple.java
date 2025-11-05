package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass({@VersionName(name="net.minecraft.inventory.SimpleInventory", end=1400), @VersionName(name="net.minecraft.inventory.BasicInventory", begin=1400, end=1600), @VersionName(name="net.minecraft.inventory.SimpleInventory", begin=1600)})
public interface InventorySimple extends WrapperObject, Inventory
{
    WrapperFactory<InventorySimple> FACTORY = WrapperFactory.of(InventorySimple.class);
    @Deprecated
    @WrapperCreator
    static InventorySimple create(Object wrapped)
    {
        return WrapperObject.create(InventorySimple.class, wrapped);
    }
    
    static InventorySimple newInstance(int size)
    {
        return FACTORY.getStatic().static$newInstance(size);
    }
    
    InventorySimple static$newInstance(int size);
    @VersionRange(end=1300)
    @WrapConstructor
    InventorySimple static$newInstanceV_1300(String name, boolean hasCustomName, int size);
    @SpecificImpl("static$newInstance")
    @VersionRange(end=1300)
    default InventorySimple static$newInstanceV_1300(int size)
    {
        return this.static$newInstanceV_1300(null, false, size);
    }
    @VersionRange(begin=1300, end=1400)
    @WrapConstructor
    InventorySimple static$newInstanceV1300_1400(Text name, int size);
    @SpecificImpl("static$newInstance")
    @VersionRange(begin=1300, end=1400)
    default InventorySimple static$newInstanceV1300_1400(int size)
    {
        return this.static$newInstanceV1300_1400(Text.FACTORY.getStatic(), size);
    }
    @SpecificImpl("static$newInstance")
    @VersionRange(begin=1400)
    @WrapConstructor
    InventorySimple static$newInstanceV1400(int size);
}
