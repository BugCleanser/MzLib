package mz.mzlib.minecraft.registry.entry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1903)
@WrapMinecraftClass(@VersionName(name="net.minecraft.registry.entry.RegistryEntryList"))
public interface RegistryEntryListV1903 extends WrapperObject
{
    WrapperFactory<RegistryEntryListV1903> FACTORY = WrapperFactory.of(RegistryEntryListV1903.class);
}
