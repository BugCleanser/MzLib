package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorClass(boolean.class)
public interface Delegator_boolean extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Delegator_boolean create(Boolean delegate)
    {
        return Delegator.create(Delegator_boolean.class, delegate);
    }

    @Override
    Boolean getDelegate();
}
