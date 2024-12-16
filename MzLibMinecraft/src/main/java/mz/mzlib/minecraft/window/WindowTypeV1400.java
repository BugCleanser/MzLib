package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.RegistriesV1903;
import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.container.ContainerType",begin=1400, end=1600), @VersionName(name="net.minecraft.screen.ScreenHandlerType", begin=1600)})
public interface WindowTypeV1400 extends WrapperObject
{
    @WrapperCreator
    static WindowTypeV1400 create(Object wrapped)
    {
        return WrapperObject.create(WindowTypeV1400.class, wrapped);
    }
    
    static Registry getRegistry()
    {
        return create(null).staticGetRegistry();
    }
    Registry staticGetRegistry();
    @SpecificImpl("staticGetRegistry")
    @VersionRange(end=1903)
    default Registry staticGetRegistryV_1903()
    {
        return Registry.windowTypeV1400_1903();
    }
    @SpecificImpl("staticGetRegistry")
    @VersionRange(begin=1903)
    default Registry staticGetRegistryV1903()
    {
        return RegistriesV1903.windowType();
    }
    
    static WindowTypeV1400 fromId(Identifier id)
    {
        return getRegistry().get(id).castTo(WindowTypeV1400::create);
    }
    static WindowTypeV1400 fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
}
