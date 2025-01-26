package mz.mzlib.minecraft.datafixer;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.serialization.DynamicV1300;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1300)
@WrapMinecraftClass(@VersionName(name="com.mojang.datafixers.DataFixer"))
public interface DataFixerV1300 extends WrapperObject
{
    @WrapperCreator
    static DataFixerV1300 create(Object wrapped)
    {
        return WrapperObject.create(DataFixerV1300.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="update"))
    DynamicV1300 update(DSLV1300.TypeReference type, DynamicV1300 data, int from, int to);
}
