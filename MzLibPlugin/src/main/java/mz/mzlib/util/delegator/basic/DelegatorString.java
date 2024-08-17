package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

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
