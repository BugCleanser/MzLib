package mz.mzlib.minecraft.incomprehensible.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.entry.RegistryEntryLookupV1903;
import mz.mzlib.minecraft.serialization.DynamicOpsV1400;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
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
    
    static DynamicOpsWithRegistriesV1903 newInstance(DynamicOpsV1400 ops, RegistryManagerV1602.Immutable registries)
    {
        return newInstance(ops, class_9683V2005.newInstance(registries));
    }
    static DynamicOpsWithRegistriesV1903 newInstance(DynamicOpsV1400 ops, class_7863 c2)
    {
        return create(null).staticNewInstance(ops, c2);
    }
    @WrapMinecraftMethod(@VersionName(name="of"))
    DynamicOpsWithRegistriesV1903 staticNewInstance(DynamicOpsV1400 ops, class_7863 c2);
    
    @WrapMinecraftInnerClass(outer=DynamicOpsWithRegistriesV1903.class, name=@VersionName(name="RegistryInfoGetter"))
    interface class_7863 extends WrapperObject
    {
        @WrapperCreator
        static class_7863 create(Object wrapped)
        {
            return WrapperObject.create(class_7863.class, wrapped);
        }
    }
    @WrapMinecraftInnerClass(outer=DynamicOpsWithRegistriesV1903.class, name=@VersionName(name="CachedRegistryInfoGetter", begin=2005))
    interface class_9683V2005 extends WrapperObject, class_7863
    {
        @WrapperCreator
        static class_9683V2005 create(Object wrapped)
        {
            return WrapperObject.create(class_9683V2005.class, wrapped);
        }
        
        static class_9683V2005 newInstance(RegistryEntryLookupV1903.class_7874 registries)
        {
            return class_9683V2005.create(null).staticNewInstance(registries);
        }
        @WrapConstructor
        class_9683V2005 staticNewInstance(RegistryEntryLookupV1903.class_7874 registries);
    }
}
