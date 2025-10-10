package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Float.class)
public interface WrapperFloat extends WrapperObject
{
    WrapperFactory<WrapperFloat> FACTORY = WrapperFactory.of(WrapperFloat.class);
    @Deprecated
    @WrapperCreator
    static WrapperFloat create(Float wrapped)
    {
        return WrapperObject.create(WrapperFloat.class, wrapped);
    }

    @Override
    Float getWrapped();

    @WrapFieldAccessor("value")
    void setValue(float value);
}
