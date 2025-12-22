package mz.mzlib.minecraft.registry.entry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.registry.RegistryWrapper", begin = 1903))
public interface RegistryWrapperV1903 extends RegistryEntryLookupV1903
{
    WrapperFactory<RegistryWrapperV1903> FACTORY = WrapperFactory.of(RegistryWrapperV1903.class);

    @WrapMinecraftInnerClass(outer = RegistryWrapperV1903.class, name = @VersionName(name = "WrapperLookup"))
    interface class_7874 extends RegistryEntryLookupV1903.RegistryLookup
    {
        WrapperFactory<class_7874> FACTORY = WrapperFactory.of(class_7874.class);
    }
}
