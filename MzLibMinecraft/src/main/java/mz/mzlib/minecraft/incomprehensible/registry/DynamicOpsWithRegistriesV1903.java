package mz.mzlib.minecraft.incomprehensible.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.entry.RegistryEntryLookupV1903;
import mz.mzlib.minecraft.serialization.DynamicOpsV1300;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.registry.RegistryOps", begin = 1903))
public interface DynamicOpsWithRegistriesV1903<T> extends WrapperObject, DynamicOpsV1300<T>
{
    WrapperFactory<DynamicOpsWithRegistriesV1903<?>> FACTORY = RuntimeUtil.cast(
        WrapperFactory.of(DynamicOpsWithRegistriesV1903.class));

    static <T> DynamicOpsWithRegistriesV1903<T> newInstance(
        DynamicOpsV1300<T> ops,
        RegistryManagerV1602.Immutable registries)
    {
        return newInstance(ops, class_9683V2005.newInstance(registries));
    }
    static <T> DynamicOpsWithRegistriesV1903<T> newInstance(DynamicOpsV1300<T> ops, class_7863 c2)
    {
        return FACTORY.getStatic().static$newInstance(ops, c2);
    }
    @WrapMinecraftMethod(@VersionName(name = "of"))
    <T1> DynamicOpsWithRegistriesV1903<T1> static$newInstance(DynamicOpsV1300<T1> ops, class_7863 c2);

    class Wrapper<T extends WrapperObject> extends DynamicOpsV1300.Wrapper<T>
    {
        public Wrapper(DynamicOpsWithRegistriesV1903<?> base, WrapperFactory<T> type)
        {
            super(base, type);
        }
    }

    @WrapMinecraftInnerClass(outer = DynamicOpsWithRegistriesV1903.class, name = @VersionName(name = "RegistryInfoGetter"))
    interface class_7863 extends WrapperObject
    {
        WrapperFactory<class_7863> FACTORY = WrapperFactory.of(class_7863.class);
        @Deprecated
        @WrapperCreator
        static class_7863 create(Object wrapped)
        {
            return WrapperObject.create(class_7863.class, wrapped);
        }
    }

    @WrapMinecraftInnerClass(outer = DynamicOpsWithRegistriesV1903.class, name = @VersionName(name = "CachedRegistryInfoGetter", begin = 2005))
    interface class_9683V2005 extends WrapperObject, class_7863
    {
        WrapperFactory<class_9683V2005> FACTORY = WrapperFactory.of(class_9683V2005.class);
        @Deprecated
        @WrapperCreator
        static class_9683V2005 create(Object wrapped)
        {
            return WrapperObject.create(class_9683V2005.class, wrapped);
        }

        static class_9683V2005 newInstance(RegistryEntryLookupV1903.class_7874 registries)
        {
            return class_9683V2005.FACTORY.getStatic().static$newInstance(registries);
        }
        @WrapConstructor
        class_9683V2005 static$newInstance(RegistryEntryLookupV1903.class_7874 registries);
    }
}
