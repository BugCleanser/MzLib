package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2105)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.component.ComponentsAccess"))
public interface ComponentsAccessV2105 extends WrapperObject
{
    WrapperFactory<ComponentsAccessV2105> FACTORY = WrapperFactory.of(ComponentsAccessV2105.class);

    @WrapMinecraftMethod(@VersionName(name = "get"))
    <T> T get0(ComponentKeyV2005<T> key);
}
