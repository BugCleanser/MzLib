package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(int.class)
public interface Wrapper_int extends WrapperObject
{
    WrapperFactory<Wrapper_int> FACTORY = WrapperFactory.find(Wrapper_int.class);
    @Deprecated
    @WrapperCreator
    static Wrapper_int create(Integer wrapped)
    {
        return WrapperObject.create(Wrapper_int.class, wrapped);
    }

    @Override
    Integer getWrapped();
}
