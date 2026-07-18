package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@Deprecated
@WrapClass(Boolean.class)
public interface WrapperBoolean extends WrapperObject
{
    WrapperFactory<WrapperBoolean> FACTORY = WrapperFactory.of(WrapperBoolean.class);

    @Override
    Boolean getWrapped();

    @WrapFieldAccessor("value")
    void setValue(boolean value);
}
