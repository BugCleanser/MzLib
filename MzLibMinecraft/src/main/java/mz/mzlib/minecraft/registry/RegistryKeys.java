package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.registry.RegistryKeys"))
public interface RegistryKeys extends WrapperObject
{
    WrapperFactory<RegistryKeys> FACTORY = WrapperFactory.of(RegistryKeys.class);

    RegistryKeyV1600 RECIPE = FACTORY.getStatic().static$RECIPE();


    @WrapMinecraftFieldAccessor(@VersionName(name = "RECIPE"))
    RegistryKeyV1600 static$RECIPE();
}
