package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorClass(short.class)
public interface Delegator_short extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Delegator_short create(Short delegate)
    {
        return Delegator.create(Delegator_short.class, delegate);
    }

    @Override
    Short getDelegate();
}
