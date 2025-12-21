package mz.mzlib.minecraft.registry.entry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.RegistryKeyV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;

// Mojang: net.minecraft.core.Holder
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.util.registry.RegistryEntry", begin = 1802, end = 1903),
    @VersionName(name = "net.minecraft.registry.entry.RegistryEntry", begin = 1903)
})
public interface RegistryEntryV1802<T> extends WrapperObject
{
    WrapperFactory<RegistryEntryV1802<?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(RegistryEntryV1802.class));

    default Option<RegistryKeyV1600> getKey()
    {
        return Option.fromOptional(this.getKey0()).map(RegistryKeyV1600.FACTORY::create);
    }

    @WrapMinecraftMethod(@VersionName(name = "comp_349"))
    T getValue();


    @WrapMinecraftMethod(@VersionName(name = "getKey"))
    Optional<Object> getKey0();

    class Wrapper<T extends WrapperObject>
    {
        RegistryEntryV1802<?> base;
        WrapperFactory<T> type;
        public Wrapper(RegistryEntryV1802<?> base, WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }

        public RegistryEntryV1802<?> getBase()
        {
            return this.base;
        }

        public Option<RegistryKeyV1600> getKey()
        {
            return this.base.getKey();
        }
        public T getValue()
        {
            return this.type.create(this.base.getValue());
        }
    }
}
