package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(long.class)
public interface Wrapper_long extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static Wrapper_long create(Long wrapped)
    {
        return WrapperObject.create(Wrapper_long.class, wrapped);
    }

    @Override
    Long getWrapped();
}
