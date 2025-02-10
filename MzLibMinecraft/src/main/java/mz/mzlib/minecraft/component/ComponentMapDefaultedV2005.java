package mz.mzlib.minecraft.component;


import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2005)
@WrapMinecraftClass({@VersionName(name="net.minecraft.component.ComponentMapImpl", end=2102), @VersionName(name="net.minecraft.component.MergedComponentMap", begin=2102)})
public interface ComponentMapDefaultedV2005 extends ComponentMapV2005
{
    @WrapperCreator
    static ComponentMapDefaultedV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentMapDefaultedV2005.class, wrapped);
    }
    
    @WrapConstructor
    ComponentMapDefaultedV2005 staticNewInstance(ComponentMapV2005 base);
    
    static ComponentMapDefaultedV2005 newInstance(ComponentMapV2005 base)
    {
        return create(null).staticNewInstance(base);
    }
}
