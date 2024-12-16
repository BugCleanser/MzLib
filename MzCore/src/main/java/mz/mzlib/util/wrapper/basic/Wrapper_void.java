package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(void.class)
public interface Wrapper_void extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static Wrapper_void create(Void wrapped)
    {
        return WrapperObject.create(Wrapper_void.class, wrapped);
    }

    @Override
    Void getWrapped();
}
