package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.ComponentType"))
public interface ComponentTypeV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentTypeV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentTypeV2005.class, wrapped);
    }
}
