package mz.mzlib.minecraft.item.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.Component", begin=2005))
public interface ComponentV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentV2005.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="comp_2443"))
    ComponentKeyV2005 getType();
    @WrapMinecraftFieldAccessor(@VersionName(name="comp_2444"))
    Object getValue();
}
