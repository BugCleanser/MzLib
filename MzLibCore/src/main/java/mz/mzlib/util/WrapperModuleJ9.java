package mz.mzlib.util;

import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@JvmVersion(begin=9)
@WrapClassForName("java.lang.Module")
public interface WrapperModuleJ9 extends WrapperObject
{
    @WrapperCreator
    static WrapperModuleJ9 create(Object wrapped)
    {
        return WrapperObject.create(WrapperModuleJ9.class, wrapped);
    }
    
    @WrapMethod("isOpen")
    boolean isOpen(String packageName, WrapperModuleJ9 target);
}
