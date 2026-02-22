package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;

/**
 * Extends {@link RegistrySimple}
 */
@WrapMinecraftClass(@VersionName(name = "net.minecraft.util.registry.DefaultedRegistry", begin = 1400, end = 1903))
public interface DefaultedRegistryV1400_1903 extends DefaultedRegistryV_1300__1400, RegistrySimple
{
    WrapperFactory<DefaultedRegistryV1400_1903> FACTORY = WrapperFactory.of(DefaultedRegistryV1400_1903.class);
    }

