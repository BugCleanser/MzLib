package mz.mzlib.minecraft.incomprehensible.resource;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1605)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.resource.SinglePreparationResourceReloader"))
public interface SinglePreparationResourceReloaderV1605<T> extends WrapperObject
{
    WrapperFactory<SinglePreparationResourceReloaderV1605<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(
        SinglePreparationResourceReloaderV1605.class));

    @WrapMinecraftMethod(@VersionName(name = "prepare"))
    T prepare(ResourceManager manager, Profiler profiler);

    @WrapMinecraftMethod(@VersionName(name = "apply"))
    void apply(T prepared, ResourceManager manager, Profiler profiler);
}
