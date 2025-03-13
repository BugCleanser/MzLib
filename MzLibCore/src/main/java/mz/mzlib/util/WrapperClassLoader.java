package mz.mzlib.util;

import mz.mzlib.util.wrapper.*;

@WrapClass(ClassLoader.class)
public interface WrapperClassLoader extends WrapperObject
{
    WrapperFactory<WrapperClassLoader> FACTORY = WrapperFactory.find(WrapperClassLoader.class);
    @Deprecated
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
