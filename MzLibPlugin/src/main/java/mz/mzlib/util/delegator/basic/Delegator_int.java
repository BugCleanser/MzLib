package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorClass(Integer.class)
public interface Delegator_int extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Delegator_int create(Integer delegate)
    {
        return Delegator.create(Delegator_int.class, delegate);
    }

    @Override
    Integer getDelegate();
}
