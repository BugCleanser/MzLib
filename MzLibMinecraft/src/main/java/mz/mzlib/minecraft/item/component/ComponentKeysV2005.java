package mz.mzlib.minecraft.item.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
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
}
