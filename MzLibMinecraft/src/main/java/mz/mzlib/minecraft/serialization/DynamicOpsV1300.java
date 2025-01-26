package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1300)
@WrapMinecraftClass({@VersionName(name="com.mojang.datafixers.types.DynamicOps", end=1600), @VersionName(name="com.mojang.serialization.DynamicOps", begin=1600)})
public interface DynamicOpsV1300 extends WrapperObject
{
}
