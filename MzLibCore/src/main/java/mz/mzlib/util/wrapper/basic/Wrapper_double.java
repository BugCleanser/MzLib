package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(double.class)
public interface Wrapper_double extends WrapperObject
{
    WrapperFactory<Wrapper_double> FACTORY = WrapperFactory.of(Wrapper_double.class);
    @Deprecated
    @WrapperCreator
    static Wrapper_double create(Double wrapped)
    {
        return WrapperObject.create(Wrapper_double.class, wrapped);
    }

    @Override
    Double getWrapped();
}
