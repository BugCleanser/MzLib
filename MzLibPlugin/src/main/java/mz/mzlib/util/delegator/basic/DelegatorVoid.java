package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(Void.class)
public interface DelegatorVoid extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorString create(String delegate)
    {
        return Delegator.create(DelegatorString.class, delegate);
    }

    @Override
    Void getDelegate();
}
