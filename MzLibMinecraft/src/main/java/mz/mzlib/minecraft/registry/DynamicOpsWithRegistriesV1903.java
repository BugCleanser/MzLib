package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.entry.RegistryEntryLookupV1903;
import mz.mzlib.minecraft.serialization.DynamicOpsV1400;
import mz.mzlib.minecraft.wrapper.WrapMinecraftChildClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.registry.RegistryOps", begin=1903))
public interface DynamicOpsWithRegistriesV1903 extends WrapperObject, DynamicOpsV1400
{
    @WrapperCreator
    static DynamicOpsWithRegistriesV1903 create(Object wrapped)
    {
        return WrapperObject.create(DynamicOpsWithRegistriesV1903.class, wrapped);
    }
    
    static DynamicOpsWithRegistriesV1903 newInstance(DynamicOpsV1400 ops, RegistrySetV1602.Immutable registries)
    {
        return newInstance(ops, C3.newInstance(registries));
    }
    static DynamicOpsWithRegistriesV1903 newInstance(DynamicOpsV1400 ops, C2 c2)
    {
        return create(null).staticNewInstance(ops, c2);
    }
    @WrapMinecraftMethod(@VersionName(name="of"))
    DynamicOpsWithRegistriesV1903 staticNewInstance(DynamicOpsV1400 ops, C2 c2);
    
    @WrapMinecraftChildClass(wrapperSupper=DynamicOpsWithRegistriesV1903.class, name=@VersionName(name="RegistryInfoGetter"))
    interface C2 extends WrapperObject
    {
        @WrapperCreator
        static C2 create(Object wrapped)
        {
            return WrapperObject.create(C2.class, wrapped);
        }
    }
    @WrapMinecraftChildClass(wrapperSupper=DynamicOpsWithRegistriesV1903.class, name=@VersionName(name="CachedRegistryInfoGetter"))
    interface C3 extends WrapperObject, C2
    {
        @WrapperCreator
        static C3 create(Object wrapped)
        {
            return WrapperObject.create(C3.class, wrapped);
        }
        
        static C3 newInstance(RegistryEntryLookupV1903.C1 registries)
        {
            return C3.create(null).staticNewInstance(registries);
        }
        @WrapConstructor
        C3 staticNewInstance(RegistryEntryLookupV1903.C1 registries);
    }
}
