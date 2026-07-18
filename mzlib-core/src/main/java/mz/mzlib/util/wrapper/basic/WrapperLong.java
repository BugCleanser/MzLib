package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@Deprecated
@WrapClass(Long.class)
public interface WrapperLong extends WrapperObject
{
    WrapperFactory<WrapperLong> FACTORY = WrapperFactory.of(WrapperLong.class);

    @Override
    Long getWrapped();

    @WrapFieldAccessor("value")
    void setValue(long value);
}
