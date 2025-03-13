package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Short.class)
public interface WrapperShort extends WrapperObject
{
    WrapperFactory<WrapperShort> FACTORY = WrapperFactory.find(WrapperShort.class);
    @Deprecated
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
