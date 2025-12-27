package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.inventory.CraftingInventory"))
public interface InventoryCrafting extends Inventory
{
    WrapperFactory<InventoryCrafting> FACTORY = WrapperFactory.of(InventoryCrafting.class);

    @VersionRange(end = 2000)
    int getWidthV_2000();
    @VersionRange(end = 2000)
    int getHeightV_2000();


    @SpecificImpl("getWidthV_2000")
    @VersionRange(end = 1300)
    @VersionRange(begin = 1400, end = 2000)
    @WrapMinecraftMethod(@VersionName(name = "getWidth"))
    int getWidthV_1300__1400_2000();
    @SpecificImpl("getHeightV_2000")
    @VersionRange(end = 1300)
    @VersionRange(begin = 1400, end = 2000)
    @WrapMinecraftMethod(@VersionName(name = "getHeight"))
    int getHeightV_1300__1400_2000();

    @SpecificImpl("getWidthV_2000")
    @VersionRange(begin = 1300, end = 1400)
    default int getWidthV_2000$implV1300_1400()
    {
        return this.getWidthV1300_1400();
    }
    @SpecificImpl("getHeightV_2000")
    @VersionRange(begin = 1300, end = 1400)
    default int getHeightV_2000$implV1300_1400()
    {
        return this.getHeightV1300_1400();
    }
}
