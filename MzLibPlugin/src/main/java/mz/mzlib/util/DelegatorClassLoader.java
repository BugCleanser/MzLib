package mz.mzlib.util;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;
import mz.mzlib.util.delegator.DelegatorMethod;

@DelegatorClass(ClassLoader.class)
public interface DelegatorClassLoader extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorClassLoader create(ClassLoader delegate)
    {
        return Delegator.create(DelegatorClassLoader.class, delegate);
    }

    @DelegatorMethod("findClass")
    Class<?> findClass(String name) throws ClassNotFoundException;

    @DelegatorMethod("findLoadedClass")
    Class<?> findLoadedClass(String name);

    @DelegatorMethod("resolveClass")
    void resolveClass(Class<?> c);
}
