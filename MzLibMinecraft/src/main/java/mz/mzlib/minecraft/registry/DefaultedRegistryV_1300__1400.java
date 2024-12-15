package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;

@WrapMinecraftClass({@VersionName(name="net.minecraft.util.registry.DefaultedRegistry", end=1300), @VersionName(name="net.minecraft.util.registry.DefaultedRegistry",begin = 1400, end=1903),@VersionName(name = "net.minecraft.registry.DefaultedRegistry", begin = 1903)})
public interface DefaultedRegistryV_1300__1400 extends Registry
{
}
