package mz.mzlib.util.wrapper.basic;

import mz.mzlib.util.wrapper.*;

@Deprecated
@WrapClass(Character.class)
public interface WrapperCharacter extends WrapperObject
{
    WrapperFactory<WrapperCharacter> FACTORY = WrapperFactory.of(WrapperCharacter.class);

    @Override
    Character getWrapped();

    @WrapFieldAccessor("value")
    void setValue(char value);
}
