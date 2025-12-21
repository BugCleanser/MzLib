package mz.mzlib.minecraft.datafixer;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.serialization.DynamicV1300;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1300)
@WrapMinecraftClass(@VersionName(name = "com.mojang.datafixers.DataFixer"))
public interface DataFixerV1300 extends WrapperObject
{
    WrapperFactory<DataFixerV1300> FACTORY = WrapperFactory.of(DataFixerV1300.class);
    @Deprecated
    @WrapperCreator
    static DataFixerV1300 create(Object wrapped)
    {
        return WrapperObject.create(DataFixerV1300.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name = "update"))
    <T> DynamicV1300<T> update(DSLV1300.TypeReference type, DynamicV1300<T> data, int from, int to);
    default <T extends WrapperObject> DynamicV1300.Wrapper<T> update(
        DSLV1300.TypeReference type,
        DynamicV1300.Wrapper<T> data,
        int from,
        int to)
    {
        return new DynamicV1300.Wrapper<>(this.update(type, data.getBase(), from, to), data.getType());
    }
}
