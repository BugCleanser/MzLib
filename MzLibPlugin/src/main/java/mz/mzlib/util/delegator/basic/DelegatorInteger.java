package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Integer.class)
public interface DelegatorInteger extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorInteger create(Integer delegate)
    {
        return Delegator.create(DelegatorInteger.class, delegate);
    }

    @Override
    Integer getDelegate();

    @DelegatorFieldAccessor("value")
    void setValue(int value);
}
