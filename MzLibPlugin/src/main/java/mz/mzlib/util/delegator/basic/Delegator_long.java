package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorClass(long.class)
public interface Delegator_long extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Delegator_long create(Long delegate)
    {
        return Delegator.create(Delegator_long.class, delegate);
    }

    @Override
    Long getDelegate();
}
