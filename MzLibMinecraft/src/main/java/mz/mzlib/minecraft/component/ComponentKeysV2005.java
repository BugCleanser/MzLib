package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.DataComponentTypes", begin=2005))
public interface ComponentKeysV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentKeysV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentKeysV2005.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="CUSTOM_DATA"))
    ComponentKeyV2005 staticCustomData();
    static ComponentKeyV2005 customData()
    {
        return create(null).staticCustomData();
    }
}
