package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@Deprecated
@WrapClass(Integer.class)
public interface WrapperInteger extends WrapperObject
{
    WrapperFactory<WrapperInteger> FACTORY = WrapperFactory.of(WrapperInteger.class);

    @Override
    Integer getWrapped();

    @WrapFieldAccessor("value")
    void setValue(int value);
}
