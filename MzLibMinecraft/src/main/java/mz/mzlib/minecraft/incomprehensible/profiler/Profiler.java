package mz.mzlib.minecraft.incomprehensible.profiler;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.util.profiler.Profiler"))
public interface Profiler extends WrapperObject
{
    WrapperFactory<Profiler> FACTORY = WrapperFactory.of(Profiler.class);
}
