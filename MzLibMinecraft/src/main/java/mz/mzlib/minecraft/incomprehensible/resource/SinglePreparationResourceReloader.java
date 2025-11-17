package mz.mzlib.minecraft.incomprehensible.resource;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.resource.SinglePreparationResourceReloader"))
public interface SinglePreparationResourceReloader<T> extends WrapperObject
{
    WrapperFactory<SinglePreparationResourceReloader<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(SinglePreparationResourceReloader.class));

    @WrapMinecraftMethod(@VersionName(name = "prepare"))
    T prepare(ResourceManager manager, Profiler profiler);
}
