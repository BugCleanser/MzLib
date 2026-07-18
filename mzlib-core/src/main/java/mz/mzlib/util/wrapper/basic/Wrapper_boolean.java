package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@Deprecated
@WrapClass(boolean.class)
public interface Wrapper_boolean extends WrapperObject
{
    WrapperFactory<Wrapper_boolean> FACTORY = WrapperFactory.of(Wrapper_boolean.class);

    @Override
    Boolean getWrapped();
}
