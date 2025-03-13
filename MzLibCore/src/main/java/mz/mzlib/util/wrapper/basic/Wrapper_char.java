package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapClass(char.class)
public interface Wrapper_char extends WrapperObject
{
    WrapperFactory<Wrapper_char> FACTORY = WrapperFactory.find(Wrapper_char.class);
    @Deprecated
    @WrapperCreator
    static Wrapper_char create(Character wrapped)
    {
        return WrapperObject.create(Wrapper_char.class, wrapped);
    }

    @Override
    Character getWrapped();
}
