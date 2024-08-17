package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorClass(void.class)
public interface Delegator_void extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Delegator_void create(Void delegate)
    {
        return Delegator.create(Delegator_void.class, delegate);
    }

    @Override
    Void getDelegate();
}
