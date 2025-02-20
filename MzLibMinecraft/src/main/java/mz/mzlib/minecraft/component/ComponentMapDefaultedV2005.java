package mz.mzlib.minecraft.component;


import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Editor;
import mz.mzlib.util.Ref;
import mz.mzlib.util.ThrowableBiFunction;
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
    
    @WrapMinecraftMethod(@VersionName(name="set"))
    WrapperObject set(ComponentKeyV2005 key, WrapperObject value);
    default <T extends WrapperObject> T set(ComponentKeyV2005.Specialized<T> key, T value)
    {
        return key.set(this, value);
    }
    
    default <T extends WrapperObject> Editor<Ref<T>> edit(ComponentKeyV2005.Specialized<T> key)
    {
        return Editor.ofRef(key, this::get, ThrowableBiFunction.<ComponentKeyV2005.Specialized<T>, T, T, RuntimeException>of(this::set).thenNothing());
    }
}
