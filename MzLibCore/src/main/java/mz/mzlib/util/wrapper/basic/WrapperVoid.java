package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(Void.class)
public interface WrapperVoid extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static WrapperString create(String wrapped)
    {
        return WrapperObject.create(WrapperString.class, wrapped);
    }

    @Override
    Void getWrapped();
}
