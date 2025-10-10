package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(Void.class)
public interface WrapperVoid extends WrapperObject
{
    WrapperFactory<WrapperVoid> FACTORY = WrapperFactory.of(WrapperVoid.class);
    @Deprecated
    @WrapperCreator
    static WrapperString create(String wrapped)
    {
        return WrapperObject.create(WrapperString.class, wrapped);
    }

    @Override
    Void getWrapped();
}
