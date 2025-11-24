package mz.mzlib.minecraft.incomprehensible.resource;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1300)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.resource.ResourceManager"))
public interface ResourceManagerV1300 extends WrapperObject
{
    WrapperFactory<ResourceManagerV1300> FACTORY = WrapperFactory.of(ResourceManagerV1300.class);
}
