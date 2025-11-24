package mz.mzlib.minecraft.incomprehensible.resource;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 1400)
@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.resource.ResourceReloadListener", end = 1400),
        @VersionName(name = "net.minecraft.class_4080", begin = 1400)
    }
)
public interface SinglePreparationResourceReloaderV1400<T> extends ResourceReloaderV1300
{
    WrapperFactory<SinglePreparationResourceReloaderV1400<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(
        SinglePreparationResourceReloaderV1400.class));

    @VersionRange(begin = 1400)
    @WrapMinecraftMethod(@VersionName(name = "prepare"))
    T prepareV1400(ResourceManagerV1300 manager, Profiler profiler);

    @VersionRange(begin = 1400)
    @WrapMinecraftMethod(@VersionName(name = "apply"))
    void applyV1400(T prepared, ResourceManagerV1300 manager, Profiler profiler);
}
