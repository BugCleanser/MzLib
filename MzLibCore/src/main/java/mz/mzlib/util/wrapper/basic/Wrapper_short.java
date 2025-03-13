package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(short.class)
public interface Wrapper_short extends WrapperObject
{
    WrapperFactory<Wrapper_short> FACTORY = WrapperFactory.find(Wrapper_short.class);
    @Deprecated
    @WrapperCreator
    static Wrapper_short create(Short wrapped)
    {
        return WrapperObject.create(Wrapper_short.class, wrapped);
    }

    @Override
    Short getWrapped();
}
