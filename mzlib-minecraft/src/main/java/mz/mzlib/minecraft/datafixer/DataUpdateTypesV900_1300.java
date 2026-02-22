package mz.mzlib.minecraft.datafixer;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 900, end = 1300)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.world.level.storage.LevelDataType"))
public interface DataUpdateTypesV900_1300 extends WrapperObject, DataUpdateTypeV900_1300
{
    WrapperFactory<DataUpdateTypesV900_1300> FACTORY = WrapperFactory.of(DataUpdateTypesV900_1300.class);
    @WrapMinecraftFieldAccessor(@VersionName(name = "field_14384"))
    DataUpdateTypeV900_1300 static$itemStack();
    static DataUpdateTypeV900_1300 itemStack()
    {
        return FACTORY.getStatic().static$itemStack();
    }
}

