package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.RegistriesV1300;
import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1400)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.container.ContainerType", end = 1600),
    @VersionName(name = "net.minecraft.screen.ScreenHandlerType", begin = 1600)
})
public interface WindowTypeV1400 extends WrapperObject
{
    WrapperFactory<WindowTypeV1400> FACTORY = WrapperFactory.of(WindowTypeV1400.class);
    @Deprecated
    @WrapperCreator
    static WindowTypeV1400 create(Object wrapped)
    {
        return WrapperObject.create(WindowTypeV1400.class, wrapped);
    }

    static Registry getRegistry()
    {
        return RegistriesV1300.windowTypeV1400();
    }

    static WindowTypeV1400 fromId(Identifier id)
    {
        return getRegistry().get(id).castTo(WindowTypeV1400.FACTORY);
    }

    static WindowTypeV1400 fromId(String id)
    {
        return fromId(Identifier.of(id));
    }
}
