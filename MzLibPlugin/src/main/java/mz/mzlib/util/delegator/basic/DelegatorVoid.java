package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

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
