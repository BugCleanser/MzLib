package mz.mzlib.minecraft.component;


import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Editor;
import mz.mzlib.util.Option;
import mz.mzlib.util.Ref;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2005)
@WrapMinecraftClass({@VersionName(name="net.minecraft.component.ComponentMapImpl", end=2102), @VersionName(name="net.minecraft.component.MergedComponentMap", begin=2102)})
public interface ComponentMapDefaultedV2005 extends ComponentMapV2005
{
    WrapperFactory<ComponentMapDefaultedV2005> FACTORY = WrapperFactory.find(ComponentMapDefaultedV2005.class);
    @Deprecated
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
    WrapperObject put(ComponentKeyV2005 key, WrapperObject value);
    default <T extends WrapperObject> Option<T> put(ComponentKeyV2005.Specialized<T> key, T value)
    {
        return key.put(this, value);
    }
    
    @WrapMinecraftMethod(@VersionName(name="remove"))
    WrapperObject remove(ComponentKeyV2005 key);
    default <T extends WrapperObject> Option<T> remove(ComponentKeyV2005.Specialized<T> key)
    {
        return key.remove(this);
    }
    
    default <T extends WrapperObject> Option<T> set(ComponentKeyV2005.Specialized<T> key, Option<T> value)
    {
        for(T t: value)
            return this.put(key, t);
        return this.remove(key);
    }
    
    default <T extends WrapperObject> Editor<Ref<Option<T>>> edit(ComponentKeyV2005.Specialized<T> key)
    {
        return Editor.ofRef(key, this::get, this::set);
    }
}
