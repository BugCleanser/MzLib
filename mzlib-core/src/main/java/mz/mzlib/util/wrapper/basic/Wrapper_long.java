package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(long.class)
public interface Wrapper_long extends WrapperObject
{
    WrapperFactory<Wrapper_long> FACTORY = WrapperFactory.of(Wrapper_long.class);

    @Override
    Long getWrapped();
}
