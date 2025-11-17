package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.util.registry.RegistryKey", begin = 1600, end = 1903),
    @VersionName(name = "net.minecraft.registry.RegistryKey", begin = 1903)
})
public interface RegistryKeyV1600 extends WrapperObject
{
    WrapperFactory<RegistryKeyV1600> FACTORY = WrapperFactory.of(RegistryKeyV1600.class);

    static RegistryKeyV1600 of(RegistryKeyV1600 registry, Identifier id)
    {
        return FACTORY.getStatic().static$of(registry, id);
    }
    @WrapMinecraftMethod(@VersionName(name = "of"))
    RegistryKeyV1600 static$of(RegistryKeyV1600 registry, Identifier id);
}
