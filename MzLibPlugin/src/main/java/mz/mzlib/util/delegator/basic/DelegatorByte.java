package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Byte.class)
public interface DelegatorByte extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static DelegatorByte create(Byte delegate)
    {
        return Delegator.create(DelegatorByte.class, delegate);
    }

    @Override
    Byte getDelegate();

    @DelegatorFieldAccessor("value")
    void setValue(byte value);
}
