package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapClass(char.class)
public interface Wrapper_char extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static Wrapper_char create(Character wrapped)
    {
        return WrapperObject.create(Wrapper_char.class, wrapped);
    }

    @Override
    Character getWrapped();
}
