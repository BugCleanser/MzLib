package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapFieldAccessor;

@WrapClass(Long.class)
public interface WrapperLong extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static WrapperLong create(Long wrapped)
    {
        return WrapperObject.create(WrapperLong.class, wrapped);
    }

    @Override
    Long getWrapped();

    @WrapFieldAccessor("value")
    void setValue(long value);
}
