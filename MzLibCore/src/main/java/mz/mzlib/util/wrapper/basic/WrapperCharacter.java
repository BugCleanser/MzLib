package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapFieldAccessor;

@WrapClass(Character.class)
public interface WrapperCharacter extends WrapperObject
{
    @WrapperCreator
    static WrapperCharacter create(Character wrapped)
    {
        return WrapperObject.create(WrapperCharacter.class, wrapped);
    }

    @Override
    Character getWrapped();

    @WrapFieldAccessor("value")
    void setValue(char value);
}
