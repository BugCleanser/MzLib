package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorClass(String.class)
public interface DelegatorString extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorString create(String delegate)
    {
        return Delegator.create(DelegatorString.class, delegate);
    }

    String getDelegate();
}
