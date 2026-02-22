package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Byte.class)
public interface WrapperByte extends WrapperObject
{
    WrapperFactory<WrapperByte> FACTORY = WrapperFactory.of(WrapperByte.class);

    @Override
    Byte getWrapped();

    @WrapFieldAccessor("value")
    void setValue(byte value);
}
