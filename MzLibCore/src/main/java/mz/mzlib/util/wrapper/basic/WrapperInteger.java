package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapFieldAccessor;

@WrapClass(Integer.class)
public interface WrapperInteger extends WrapperObject
{
    @WrapperCreator
    static WrapperInteger create(Integer wrapped)
    {
        return WrapperObject.create(WrapperInteger.class, wrapped);
    }

    @Override
    Integer getWrapped();

    @WrapFieldAccessor("value")
    void setValue(int value);
}
