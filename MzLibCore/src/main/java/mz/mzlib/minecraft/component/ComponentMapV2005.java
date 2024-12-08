package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.IteratorWrapper;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Iterator;
import java.util.function.Function;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.ComponentMap", begin=2005))
public interface ComponentMapV2005 extends WrapperObject,Iterable<ComponentV2005>
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

    @SuppressWarnings("NullableProblems")
    @Override
    default Iterator<ComponentV2005> iterator()
    {
        return new IteratorWrapper<>(this.getWrapped().iterator(), ComponentV2005::create);
    }
}
