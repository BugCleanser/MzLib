package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(Integer.class)
public interface Wrapper_int extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static Wrapper_int create(Integer wrapped)
    {
        return WrapperObject.create(Wrapper_int.class, wrapped);
    }

    @Override
    Integer getWrapped();
}
