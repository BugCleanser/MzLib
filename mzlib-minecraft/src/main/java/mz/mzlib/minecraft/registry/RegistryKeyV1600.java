package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.util.registry.RegistryKey", begin = 1600, end = 1903),
    @VersionName(name = "net.minecraft.registry.RegistryKey", begin = 1903)
})
public interface RegistryKeyV1600<T> extends WrapperObject
{
    WrapperFactory<RegistryKeyV1600<?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(RegistryKeyV1600.class));

    static <T extends WrapperObject> RegistryKeyV1600<T> of(RegistryKeyV1600<Registry.Wrapper<T>> registry, Identifier id)
    {
        return FACTORY.getStatic().static$of(registry, id);
    }
    static <T extends WrapperObject> RegistryKeyV1600<Registry.Wrapper<T>> ofRegistry(Identifier id)
    {
        return FACTORY.getStatic().static$ofRegistry(id);
    }

    @WrapMinecraftMethod(@VersionName(name = "getValue"))
    Identifier getId();

    @WrapMinecraftMethod(@VersionName(name = "of"))
    <T1 extends WrapperObject> RegistryKeyV1600<T1> static$of(RegistryKeyV1600<Registry.Wrapper<T1>> registry, Identifier id);

    @WrapMinecraftMethod(@VersionName(name = "ofRegistry"))
    <T1 extends WrapperObject> RegistryKeyV1600<Registry.Wrapper<T1>> static$ofRegistry(Identifier id);
}
