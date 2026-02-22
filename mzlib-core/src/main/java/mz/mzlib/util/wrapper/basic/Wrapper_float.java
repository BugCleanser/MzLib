package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(float.class)
public interface Wrapper_float extends WrapperObject
{
    WrapperFactory<Wrapper_float> FACTORY = WrapperFactory.of(Wrapper_float.class);

    @Override
    Float getWrapped();
}
