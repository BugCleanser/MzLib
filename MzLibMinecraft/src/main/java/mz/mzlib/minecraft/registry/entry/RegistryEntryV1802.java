package mz.mzlib.minecraft.registry.entry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.RegistryKeyV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;

// Mojang: net.minecraft.core.Holder
@WrapMinecraftClass({@VersionName(name="net.minecraft.util.registry.RegistryEntry", begin=1802, end=1903),@VersionName(name = "net.minecraft.registry.entry.RegistryEntry", begin = 1903)})
public interface RegistryEntryV1802 extends WrapperObject
{
    @WrapperCreator
    static RegistryEntryV1802 create(Object wrapped)
    {
        return WrapperObject.create(RegistryEntryV1802.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name="getKey"))
    Optional<Object> getKey0();
    default Optional<RegistryKeyV1600> getKey()
    {
        return getKey0().map(RegistryKeyV1600::create);
    }
}
