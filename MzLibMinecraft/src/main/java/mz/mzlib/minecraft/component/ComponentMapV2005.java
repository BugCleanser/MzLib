package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.proxy.IteratorProxy;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Iterator;
import java.util.function.Function;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.ComponentMap", begin=2005))
public interface ComponentMapV2005 extends WrapperObject,Iterable<ComponentMapV2005.Entry>
{
    WrapperFactory<ComponentMapV2005> FACTORY = WrapperFactory.of(ComponentMapV2005.class);
    @Deprecated
    @WrapperCreator
    static ComponentMapV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentMapV2005.class, wrapped);
    }
    
    @Override
    Iterable<Object> getWrapped();
    
    WrapperObject get(ComponentKeyV2005 key);
    @VersionRange(end=2105)
    @SpecificImpl("get")
    @WrapMinecraftMethod(@VersionName(name="get"))
    WrapperObject getV_2105(ComponentKeyV2005 key);
    @VersionRange(begin=2105)
    @SpecificImpl("get")
    default WrapperObject getV2105(ComponentKeyV2005 key)
    {
        return this.castTo(ComponentsAccessV2105.FACTORY).get(key);
    }
    default <T extends WrapperObject> Option<T> get(ComponentKeyV2005 key, WrapperFactory<T> factory)
    {
        return Option.fromWrapper(this.get(key).castTo(factory));
    }
    @Deprecated
    default <T extends WrapperObject> T get(ComponentKeyV2005 key, Function<Object, T> creator)
    {
        return this.get(key).castTo(creator);
    }
    
    default <T extends WrapperObject> Option<T> get(ComponentKeyV2005.Wrapper<T> key)
    {
        return key.get(this);
    }
    
    default <T extends WrapperObject> Option<T> copy(ComponentKeyV2005.Wrapper<T> key)
    {
        return this.get(key).map(key::copy);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    default Iterator<Entry> iterator()
    {
        return new IteratorProxy<>(this.getWrapped().iterator(), Entry.FACTORY::create);
    }
    
    @WrapMinecraftClass(@VersionName(name="net.minecraft.component.Component", begin=2005))
    interface Entry extends WrapperObject
    {
        WrapperFactory<Entry> FACTORY = WrapperFactory.of(Entry.class);
        @Deprecated
        @WrapperCreator
        static Entry create(Object wrapped)
        {
            return WrapperObject.create(Entry.class, wrapped);
        }
    
        @WrapMinecraftFieldAccessor(@VersionName(name="comp_2443"))
        ComponentKeyV2005 getType();
        @WrapMinecraftFieldAccessor(@VersionName(name="comp_2444"))
        Object getValue();
    }
}
