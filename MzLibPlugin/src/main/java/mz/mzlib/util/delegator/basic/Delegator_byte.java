package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorClass(byte.class)
public interface Delegator_byte extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Delegator_byte create(Byte delegate)
    {
        return Delegator.create(Delegator_byte.class, delegate);
    }

    @Override
    Byte getDelegate();
}


