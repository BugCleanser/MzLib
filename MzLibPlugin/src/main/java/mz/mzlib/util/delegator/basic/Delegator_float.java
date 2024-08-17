package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorClass(float.class)
public interface Delegator_float extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Delegator_float create(Float delegate)
    {
        return Delegator.create(Delegator_float.class, delegate);
    }

    @Override
    Float getDelegate();
}
