package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Short.class)
public interface WrapperShort extends WrapperObject
{
    WrapperFactory<WrapperShort> FACTORY = WrapperFactory.of(WrapperShort.class);

    @Override
    Short getWrapped();

    @WrapFieldAccessor("value")
    void setValue(short value);
}
