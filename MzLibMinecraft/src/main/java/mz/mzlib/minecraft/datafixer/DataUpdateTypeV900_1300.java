package mz.mzlib.minecraft.datafixer;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=900, end=1300)
@WrapMinecraftClass(@VersionName(name="net.minecraft.datafixer.DataFixType"))
public interface DataUpdateTypeV900_1300 extends WrapperObject
{
    @WrapperCreator
    static DataUpdateTypeV900_1300 create(Object wrapped)
    {
        return WrapperObject.create(DataUpdateTypeV900_1300.class, wrapped);
    }
}
