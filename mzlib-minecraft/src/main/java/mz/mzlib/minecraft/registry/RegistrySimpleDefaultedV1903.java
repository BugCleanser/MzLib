package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.registry.SimpleDefaultedRegistry", begin = 1903))
public interface RegistrySimpleDefaultedV1903<T> extends RegistrySimple<T>, DefaultedRegistryV_1300__1400<T>
{
}
