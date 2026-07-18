package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@Deprecated
@WrapClass(byte.class)
public interface Wrapper_byte extends WrapperObject
{
    WrapperFactory<Wrapper_byte> FACTORY = WrapperFactory.of(Wrapper_byte.class);

    @Override
    Byte getWrapped();
}


