package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapFieldAccessor;

@WrapClass(Float.class)
public interface WrapperFloat extends WrapperObject
{
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
