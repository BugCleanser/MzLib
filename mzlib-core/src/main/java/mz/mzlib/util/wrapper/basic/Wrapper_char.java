package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@Deprecated
@WrapClass(char.class)
public interface Wrapper_char extends WrapperObject
{
    WrapperFactory<Wrapper_char> FACTORY = WrapperFactory.of(Wrapper_char.class);

    @Override
    Character getWrapped();
}
