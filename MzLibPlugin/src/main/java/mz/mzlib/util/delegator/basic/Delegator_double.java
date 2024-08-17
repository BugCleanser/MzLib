package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorClass(double.class)
public interface Delegator_double extends Delegator
{

    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Delegator_double create(Double delegate)
    {
        return Delegator.create(Delegator_double.class, delegate);
    }

    @Override
    Double getDelegate();
}
