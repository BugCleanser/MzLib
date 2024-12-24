package mz.mzlib.minecraft.item.component;


import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.ComponentMapImpl", begin=2005))
public interface ComponentMapMutableV2005 extends ComponentMapV2005
{
    @WrapperCreator
    static ComponentMapMutableV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentMapMutableV2005.class, wrapped);
    }

    @WrapConstructor
    ComponentMapMutableV2005 staticNewInstance(ComponentMapV2005 base);
    static ComponentMapMutableV2005 newInstance(ComponentMapV2005 base)
    {
        return create(null).staticNewInstance(base);
    }
}
