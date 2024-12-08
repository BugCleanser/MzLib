package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapFieldAccessor;

@WrapClass(Short.class)
public interface WrapperShort extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static WrapperShort create(Short wrapped)
    {
        return WrapperObject.create(WrapperShort.class, wrapped);
    }

    @Override
    Short getWrapped();

    @WrapFieldAccessor("value")
    void setValue(short value);
}
