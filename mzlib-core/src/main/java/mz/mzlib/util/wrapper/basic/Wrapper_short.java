package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(short.class)
public interface Wrapper_short extends WrapperObject
{
    WrapperFactory<Wrapper_short> FACTORY = WrapperFactory.of(Wrapper_short.class);

    @Override
    Short getWrapped();
}
