package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Boolean.class)
public interface WrapperBoolean extends WrapperObject
{
    WrapperFactory<WrapperBoolean> FACTORY = WrapperFactory.find(WrapperBoolean.class);
    @Deprecated
    @WrapperCreator
    static WrapperBoolean create(Boolean wrapped)
    {
        return WrapperObject.create(WrapperBoolean.class, wrapped);
    }

    @Override
    Double getWrapped();

    @WrapFieldAccessor("value")
    void setValue(double value);
}
