package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.IteratorWrapper;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Iterator;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.ComponentMap"))
public interface ComponentMapV2005 extends WrapperObject,Iterable<ComponentV2005>
{
    @Override
    Iterable<Object> getWrapped();

    @WrapperCreator
    static ComponentMapV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentMapV2005.class, wrapped);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    default Iterator<ComponentV2005> iterator()
    {
        return new IteratorWrapper<>(this.getWrapped().iterator(), ComponentV2005::create);
    }
}
