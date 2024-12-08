package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.registry.Registries",begin=1903))
public interface RegistriesV1903 extends WrapperObject
{
    @WrapperCreator
    static RegistriesV1903 create(Object wrapped)
    {
        return WrapperObject.create(RegistriesV1903.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="ITEM"))
    DefaultedRegistryV_1300__1400 staticItem();
    static DefaultedRegistryV_1300__1400 item()
    {
        return create(null).staticItem();
    }
}
