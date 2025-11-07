package mz.mzlib.minecraft.incomprehensible.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.entry.RegistryEntryLookupV1903;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

// Mojang: net.minecraft.core.RegistryAccess
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.util.registry.DynamicRegistryManager", begin = 1602, end = 1903),
    @VersionName(name = "net.minecraft.registry.DynamicRegistryManager", begin = 1903)
})
public interface RegistryManagerV1602 extends WrapperObject, RegistryEntryLookupV1903.class_7874
{
    WrapperFactory<RegistryManagerV1602> FACTORY = WrapperFactory.of(RegistryManagerV1602.class);
    @Deprecated
    @WrapperCreator
    static RegistryManagerV1602 create(Object wrapped)
    {
        return WrapperObject.create(RegistryManagerV1602.class, wrapped);
    }

    @WrapMinecraftInnerClass(outer = RegistryManagerV1602.class, name = @VersionName(name = "Immutable", begin = 1802))
    interface Immutable extends RegistryManagerV1602
    {
        WrapperFactory<Immutable> FACTORY = WrapperFactory.of(Immutable.class);
        @Deprecated
        @WrapperCreator
        static Immutable create(Object wrapped)
        {
            return WrapperObject.create(Immutable.class, wrapped);
        }
    }
}
