package mz.mzlib.util;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapMethod;

@WrapClass(ClassLoader.class)
public interface WrapperClassLoader extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static WrapperClassLoader create(ClassLoader wrapped)
    {
        return WrapperObject.create(WrapperClassLoader.class, wrapped);
    }

    @WrapMethod("findClass")
    Class<?> findClass(String name) throws ClassNotFoundException;

    @WrapMethod("findLoadedClass")
    Class<?> findLoadedClass(String name);

    @WrapMethod("resolveClass")
    void resolveClass(Class<?> c);
}
