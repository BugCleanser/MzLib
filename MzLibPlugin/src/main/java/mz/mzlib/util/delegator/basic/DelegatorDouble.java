package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Double.class)
public interface DelegatorDouble extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorDouble create(Double delegate)
    {
        return Delegator.create(DelegatorDouble.class, delegate);
    }

    @Override
    Double getDelegate();

    @DelegatorFieldAccessor("value")
    void setValue(double value);
}
