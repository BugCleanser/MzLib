package mz.mzlib.util;

import mz.mzlib.util.wrapper.*;

@WrapClass(Class.class)
public interface WrapperClass extends WrapperObject
{
    WrapperFactory<WrapperClass> FACTORY = WrapperFactory.find(WrapperClass.class);
    @Deprecated
    @WrapperCreator
    static WrapperClass create(Object wrapped)
    {
        return WrapperObject.create(WrapperClass.class, wrapped);
    }
    
    @JvmVersion(begin=9)
    @WrapMethod("getModule")
    WrapperModuleJ9 getModuleJ9();
}
