package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(byte.class)
public interface ByteDelegator extends Delegator
{
	@Override
	Byte getDelegate();
}
