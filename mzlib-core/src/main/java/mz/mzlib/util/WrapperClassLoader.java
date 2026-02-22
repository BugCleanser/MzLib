package mz.mzlib.util;

import mz.mzlib.util.wrapper.*;

@WrapClass(ClassLoader.class)
public interface WrapperClassLoader extends WrapperObject
{
    WrapperFactory<WrapperClassLoader> FACTORY = WrapperFactory.of(WrapperClassLoader.class);

    @WrapMethod("findClass")
    Class<?> findClass(String name) throws ClassNotFoundException;

    @WrapMethod("findLoadedClass")
    Class<?> findLoadedClass(String name);

    @WrapMethod("resolveClass")
    void resolveClass(Class<?> c);
}
