package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(float.class)
public interface Wrapper_float extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static Wrapper_float create(Float wrapped)
    {
        return WrapperObject.create(Wrapper_float.class, wrapped);
    }

    @Override
    Float getWrapped();
}
