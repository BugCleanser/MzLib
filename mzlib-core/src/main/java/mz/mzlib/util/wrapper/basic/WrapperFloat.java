package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@Deprecated
@WrapClass(Float.class)
public interface WrapperFloat extends WrapperObject
{
    WrapperFactory<WrapperFloat> FACTORY = WrapperFactory.of(WrapperFloat.class);

    @Override
    Float getWrapped();

    @WrapFieldAccessor("value")
    void setValue(float value);
}
