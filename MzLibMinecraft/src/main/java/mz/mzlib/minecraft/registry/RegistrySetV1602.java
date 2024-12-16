package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.entry.RegistryEntryLookupV1903;
import mz.mzlib.minecraft.wrapper.WrapMinecraftChildClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

// Mojang: net.minecraft.core.RegistryAccess
@WrapMinecraftClass({@VersionName(name="net.minecraft.util.registry.DynamicRegistryManager", begin=1602, end=1903), @VersionName(name="net.minecraft.registry.DynamicRegistryManager", begin=1903)})
public interface RegistrySetV1602 extends WrapperObject, RegistryEntryLookupV1903.C1
{
    @WrapperCreator
    static RegistrySetV1602 create(Object wrapped)
    {
        return WrapperObject.create(RegistrySetV1602.class, wrapped);
    }
    
    @WrapMinecraftChildClass(wrapperSupper=RegistrySetV1602.class, name=@VersionName(name="Immutable", begin=1802))
    interface Immutable extends RegistrySetV1602
    {
        @WrapperCreator
        static Immutable create(Object wrapped)
        {
            return WrapperObject.create(Immutable.class, wrapped);
        }
    }
}
