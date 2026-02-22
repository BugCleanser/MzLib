package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Boolean.class)
public interface WrapperBoolean extends WrapperObject
{
    WrapperFactory<WrapperBoolean> FACTORY = WrapperFactory.of(WrapperBoolean.class);

    @Override
    Double getWrapped();

    @WrapFieldAccessor("value")
    void setValue(double value);
}
