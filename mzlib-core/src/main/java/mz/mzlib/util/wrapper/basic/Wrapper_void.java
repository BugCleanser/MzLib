package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@Deprecated
@WrapClass(void.class)
public interface Wrapper_void extends WrapperObject
{
    WrapperFactory<Wrapper_void> FACTORY = WrapperFactory.of(Wrapper_void.class);

    @Override
    Void getWrapped();
}
