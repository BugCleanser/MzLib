package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Double.class)
public interface WrapperDouble extends WrapperObject
{
    WrapperFactory<WrapperDouble> FACTORY = WrapperFactory.find(WrapperDouble.class);
    @Deprecated
    @WrapperCreator
    static WrapperDouble create(Double wrapped)
    {
        return WrapperObject.create(WrapperDouble.class, wrapped);
    }

    @Override
    Double getWrapped();

    @WrapFieldAccessor("value")
    void setValue(double value);
}
