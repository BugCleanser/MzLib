package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(boolean.class)
public interface Wrapper_boolean extends WrapperObject
{
    WrapperFactory<Wrapper_boolean> FACTORY = WrapperFactory.of(Wrapper_boolean.class);
    @Deprecated
    @WrapperCreator
    static Wrapper_boolean create(Boolean wrapped)
    {
        return WrapperObject.create(Wrapper_boolean.class, wrapped);
    }

    @Override
    Boolean getWrapped();
}
