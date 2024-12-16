package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(byte.class)
public interface Wrapper_byte extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static Wrapper_byte create(Byte wrapped)
    {
        return WrapperObject.create(Wrapper_byte.class, wrapped);
    }

    @Override
    Byte getWrapped();
}


