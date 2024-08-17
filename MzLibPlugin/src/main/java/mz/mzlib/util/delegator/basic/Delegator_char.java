package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorClass(char.class)
public interface Delegator_char extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Delegator_char create(Character delegate)
    {
        return Delegator.create(Delegator_char.class, delegate);
    }

    @Override
    Character getDelegate();
}
