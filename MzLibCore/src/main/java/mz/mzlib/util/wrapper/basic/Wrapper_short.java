package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(short.class)
public interface Wrapper_short extends WrapperObject
{
    @WrapperCreator
    static Wrapper_short create(Short wrapped)
    {
        return WrapperObject.create(Wrapper_short.class, wrapped);
    }

    @Override
    Short getWrapped();
}
