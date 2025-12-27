package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1600)
@WrapMinecraftClass(@VersionName(name = "com.mojang.serialization.MapCodec"))
public interface MapCodecV1600<T> extends WrapperObject
{
    WrapperFactory<MapCodecV1600<?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(MapCodecV1600.class));
}
