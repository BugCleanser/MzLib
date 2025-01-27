package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;

@WrapClass(int.class)
public interface Wrapper_int extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @mz.mzlib.util.wrapper.WrapperCreator
    static Wrapper_int create(Integer wrapped)
    {
        return WrapperObject.create(Wrapper_int.class, wrapped);
    }

    @Override
    Integer getWrapped();
}
