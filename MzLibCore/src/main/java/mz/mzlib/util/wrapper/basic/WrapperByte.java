package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapFieldAccessor;

@WrapClass(Byte.class)
public interface WrapperByte extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static WrapperByte create(Byte wrapped)
    {
        return WrapperObject.create(WrapperByte.class, wrapped);
    }

    @Override
    Byte getWrapped();

    @WrapFieldAccessor("value")
    void setValue(byte value);
}
