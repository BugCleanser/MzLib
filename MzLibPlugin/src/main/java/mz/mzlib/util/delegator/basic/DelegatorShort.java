package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Short.class)
public interface DelegatorShort extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorShort create(Short delegate)
    {
        return Delegator.create(DelegatorShort.class, delegate);
    }

    @Override
    Short getDelegate();

    @DelegatorFieldAccessor("value")
    void setValue(short value);
}
