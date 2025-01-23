package mz.mzlib.util;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(Class.class)
public interface WrapperClass extends WrapperObject
{
    @WrapperCreator
    static WrapperClass create(Object wrapped)
    {
        return WrapperObject.create(WrapperClass.class, wrapped);
    }
    
    @JvmVersion(begin=9)
    @WrapMethod("getModule")
    WrapperModuleJ9 getModuleJ9();
}
