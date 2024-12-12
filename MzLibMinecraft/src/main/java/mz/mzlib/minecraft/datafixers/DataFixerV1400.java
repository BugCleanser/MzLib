package mz.mzlib.minecraft.datafixers;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.serialization.DynamicV1400;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.datafixers.DataFixer", begin=1400))
public interface DataFixerV1400 extends WrapperObject
{
    @WrapperCreator
    static DataFixerV1400 create(Object wrapped)
    {
        return WrapperObject.create(DataFixerV1400.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="update"))
    DynamicV1400 update(DSLV1400.TypeReference type, DynamicV1400 data, int from, int to);
}
