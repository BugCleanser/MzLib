package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapFieldAccessor;

@WrapClass(Double.class)
public interface WrapperDouble extends WrapperObject
{
    @SuppressWarnings("deprecation")
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
