package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(double.class)
public interface Wrapper_double extends WrapperObject
{
    @WrapperCreator
    static Wrapper_double create(Double wrapped)
    {
        return WrapperObject.create(Wrapper_double.class, wrapped);
    }

    @Override
    Double getWrapped();
}
