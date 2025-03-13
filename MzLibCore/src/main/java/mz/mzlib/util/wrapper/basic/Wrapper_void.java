package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(void.class)
public interface Wrapper_void extends WrapperObject
{
    WrapperFactory<Wrapper_void> FACTORY = WrapperFactory.find(Wrapper_void.class);
    @Deprecated
    @WrapperCreator
    static Wrapper_void create(Void wrapped)
    {
        return WrapperObject.create(Wrapper_void.class, wrapped);
    }

    @Override
    Void getWrapped();
}
