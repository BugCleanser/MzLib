package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.ComponentType"))
public interface ComponentKeyV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentKeyV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentKeyV2005.class, wrapped);
    }
}
