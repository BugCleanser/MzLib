package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.proxy.IteratorProxy;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Iterator;
import java.util.function.Function;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.ComponentMap", begin=2005))
public interface ComponentMapV2005 extends WrapperObject,Iterable<ComponentMapV2005.Entry>
{
    @Override
    Iterable<Object> getWrapped();

    @WrapperCreator
    static ComponentMapV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentMapV2005.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name="get"))
    WrapperObject get(ComponentKeyV2005 key);
    default <T extends WrapperObject> T get(ComponentKeyV2005 key, Function<Object, T> wrapperCreator)
    {
        return this.get(key).castTo(wrapperCreator);
    }
    default <T extends WrapperObject> T get(ComponentKeyV2005.Specialized<T> key)
    {
        return key.get(this);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    default Iterator<Entry> iterator()
    {
        return new IteratorProxy<>(this.getWrapped().iterator(), Entry::create);
    }
    
    @WrapMinecraftClass(@VersionName(name="net.minecraft.component.Component", begin=2005))
    interface Entry extends WrapperObject
    {
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
