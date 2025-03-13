package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(float.class)
public interface Wrapper_float extends WrapperObject
{
    WrapperFactory<Wrapper_float> FACTORY = WrapperFactory.find(Wrapper_float.class);
    @Deprecated
    @WrapperCreator
    static Wrapper_float create(Float wrapped)
    {
        return WrapperObject.create(Wrapper_float.class, wrapped);
    }

    @Override
    Float getWrapped();
}
