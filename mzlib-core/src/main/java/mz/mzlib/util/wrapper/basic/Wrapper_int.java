package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(int.class)
public interface Wrapper_int extends WrapperObject
{
    WrapperFactory<Wrapper_int> FACTORY = WrapperFactory.of(Wrapper_int.class);

    @Override
    Integer getWrapped();
}
