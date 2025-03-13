package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(String.class)
public interface WrapperString extends WrapperObject
{
    WrapperFactory<WrapperString> FACTORY = WrapperFactory.find(WrapperString.class);
    @Deprecated
    @WrapperCreator
    static WrapperString create(String wrapped)
    {
        return WrapperObject.create(WrapperString.class, wrapped);
    }

    String getWrapped();
}
