package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.registry.SimpleDefaultedRegistry", begin = 1903))
public interface SimpleDefaultedRegistryV1903 extends WrapperObject, SimpleRegistry, DefaultedRegistryV_1300__1400
{
}
