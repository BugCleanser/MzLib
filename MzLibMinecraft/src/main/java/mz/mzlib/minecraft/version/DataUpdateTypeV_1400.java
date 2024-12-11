package mz.mzlib.minecraft.version;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.datafixer.DataFixType", end=1400))
public interface DataUpdateTypeV_1400 extends WrapperObject
{
    @WrapperCreator
    static DataUpdateTypeV_1400 create(Object wrapped)
    {
        return WrapperObject.create(DataUpdateTypeV_1400.class, wrapped);
    }
}
