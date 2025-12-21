package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Integer.class)
public interface WrapperInteger extends WrapperObject
{
    WrapperFactory<WrapperInteger> FACTORY = WrapperFactory.of(WrapperInteger.class);
    @Deprecated
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
