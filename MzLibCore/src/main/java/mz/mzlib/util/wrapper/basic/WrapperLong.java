package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Long.class)
public interface WrapperLong extends WrapperObject
{
    WrapperFactory<WrapperLong> FACTORY = WrapperFactory.find(WrapperLong.class);
    @Deprecated
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
