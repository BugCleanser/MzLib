package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(boolean.class)
public interface Wrapper_boolean extends WrapperObject
{
    @WrapperCreator
    static Wrapper_boolean create(Boolean wrapped)
    {
        return WrapperObject.create(Wrapper_boolean.class, wrapped);
    }

    @Override
    Boolean getWrapped();
}
