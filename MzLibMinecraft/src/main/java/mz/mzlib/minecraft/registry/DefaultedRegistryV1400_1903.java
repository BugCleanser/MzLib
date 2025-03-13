package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

/**
 * Extends {@link SimpleRegistry}
 */
@WrapMinecraftClass(@VersionName(name="net.minecraft.util.registry.DefaultedRegistry",begin = 1400, end=1903))
public interface DefaultedRegistryV1400_1903 extends DefaultedRegistryV_1300__1400, SimpleRegistry
{
    WrapperFactory<DefaultedRegistryV1400_1903> FACTORY = WrapperFactory.find(DefaultedRegistryV1400_1903.class);
    @Deprecated
    @WrapperCreator
    static DefaultedRegistryV1400_1903 create(Object wrapped)
    {
        return WrapperObject.create(DefaultedRegistryV1400_1903.class, wrapped);
    }
}
