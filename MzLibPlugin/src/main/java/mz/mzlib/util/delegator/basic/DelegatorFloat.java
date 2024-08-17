package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Float.class)
public interface DelegatorFloat extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorFloat create(Float delegate)
    {
        return Delegator.create(DelegatorFloat.class, delegate);
    }

    @Override
    Float getDelegate();

    @DelegatorFieldAccessor("value")
    void setValue(float value);
}
