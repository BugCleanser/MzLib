package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@WrapClass(Character.class)
public interface WrapperCharacter extends WrapperObject
{
    WrapperFactory<WrapperCharacter> FACTORY = WrapperFactory.of(WrapperCharacter.class);
    @Deprecated
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
