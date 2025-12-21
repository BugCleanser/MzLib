package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(byte.class)
public interface Wrapper_byte extends WrapperObject
{
    WrapperFactory<Wrapper_byte> FACTORY = WrapperFactory.of(Wrapper_byte.class);
    @Deprecated
    @WrapperCreator
    static Wrapper_byte create(Byte wrapped)
    {
        return WrapperObject.create(Wrapper_byte.class, wrapped);
    }

    @Override
    Byte getWrapped();
}


