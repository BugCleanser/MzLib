package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(double.class)
public interface Wrapper_double extends WrapperObject
{
    WrapperFactory<Wrapper_double> FACTORY = WrapperFactory.of(Wrapper_double.class);

    @Override
    Double getWrapped();
}
