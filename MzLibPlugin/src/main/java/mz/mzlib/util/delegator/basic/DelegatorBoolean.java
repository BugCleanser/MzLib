package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Double.class)
public interface DelegatorBoolean extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorBoolean create(Boolean delegate)
    {
        return Delegator.create(DelegatorBoolean.class, delegate);
    }

    @Override
    Double getDelegate();

    @DelegatorFieldAccessor("value")
    void setValue(double value);
}
