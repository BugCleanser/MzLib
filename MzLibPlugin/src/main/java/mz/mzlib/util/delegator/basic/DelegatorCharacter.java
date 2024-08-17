package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Character.class)
public interface DelegatorCharacter extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorCharacter create(Character delegate)
    {
        return Delegator.create(DelegatorCharacter.class, delegate);
    }

    @Override
    Character getDelegate();

    @DelegatorFieldAccessor("value")
    void setValue(char value);
}
