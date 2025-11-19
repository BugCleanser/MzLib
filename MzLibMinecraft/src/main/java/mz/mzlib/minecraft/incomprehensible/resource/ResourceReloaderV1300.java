package mz.mzlib.minecraft.incomprehensible.resource;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1300)
@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.resource.ResourceReloadListener", end = 1400),
        @VersionName(name = "net.minecraft.class_3302", begin = 1400)
    }
)
public interface ResourceReloaderV1300 extends WrapperObject
{
    WrapperFactory<ResourceReloaderV1300> FACTORY = WrapperFactory.of(ResourceReloaderV1300.class);

    @VersionRange(end = 1400)
    @WrapMinecraftMethod(@VersionName(name = "reload"))
    void reloadV_1400(ResourceManager resourceManager);
}
