package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(String.class)
public interface WrapperString extends WrapperObject
{
    @WrapperCreator
    static WrapperString create(String wrapped)
    {
        return WrapperObject.create(WrapperString.class, wrapped);
    }

    String getWrapped();
}
