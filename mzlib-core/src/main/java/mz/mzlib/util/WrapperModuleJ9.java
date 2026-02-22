package mz.mzlib.util;

import mz.mzlib.util.wrapper.*;

@JvmVersion(begin = 9)
@WrapClassForName("java.lang.Module")
public interface WrapperModuleJ9 extends WrapperObject
{
    WrapperFactory<WrapperModuleJ9> FACTORY = WrapperFactory.of(WrapperModuleJ9.class);

    @WrapMethod("isOpen")
    boolean isOpen(String packageName, WrapperModuleJ9 target);

    @WrapMethod("isExported")
    boolean isExported(String packageName, WrapperModuleJ9 target);
}
