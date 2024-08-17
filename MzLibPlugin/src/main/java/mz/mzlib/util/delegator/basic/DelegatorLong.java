package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Long.class)
public interface DelegatorLong extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorLong create(Long delegate)
    {
        return Delegator.create(DelegatorLong.class, delegate);
    }

    @Override
    Long getDelegate();

    @DelegatorFieldAccessor("value")
    void setValue(long value);
}
