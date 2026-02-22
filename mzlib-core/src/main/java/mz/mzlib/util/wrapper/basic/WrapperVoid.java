package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(Void.class)
public interface WrapperVoid extends WrapperObject
{
    WrapperFactory<WrapperVoid> FACTORY = WrapperFactory.of(WrapperVoid.class);

    @Override
    Void getWrapped();
}
