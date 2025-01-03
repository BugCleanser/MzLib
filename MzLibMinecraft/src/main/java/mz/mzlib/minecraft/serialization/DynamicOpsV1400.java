package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="com.mojang.datafixers.types.DynamicOps", begin=1400, end=1600), @VersionName(name="com.mojang.serialization.DynamicOps", begin=1600)})
public interface DynamicOpsV1400 extends WrapperObject
{
}
