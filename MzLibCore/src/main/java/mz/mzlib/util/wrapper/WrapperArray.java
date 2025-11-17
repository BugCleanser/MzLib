package mz.mzlib.util.wrapper;

import mz.mzlib.util.RuntimeUtil;

import java.lang.reflect.Array;

@WrapArrayClass(WrapperObject.class)
public interface WrapperArray<T extends WrapperObject> extends WrapperObject
{
    WrapperFactory<T> static$getElementFactory();

    default WrapperArray<T> static$newInstance(int length)
    {
        return RuntimeUtil.cast(this.static$create(Array.newInstance(this.static$getElementFactory().getStatic().static$getWrappedClass(), length)));
    }

    default T get(int index)
    {
        return this.static$getElementFactory().create(Array.get(this.getWrapped(), index));
    }

    default void set(int index, T value)
    {
        Array.set(this.getWrapped(), index, value.getWrapped());
    }

    default int length()
    {
        return Array.getLength(this.getWrapped());
    }
}
