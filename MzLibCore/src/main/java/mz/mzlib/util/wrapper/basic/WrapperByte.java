package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Byte.class)
public interface WrapperByte extends WrapperObject
{
    WrapperFactory<WrapperByte> FACTORY = WrapperFactory.find(WrapperByte.class);
    @Deprecated
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
