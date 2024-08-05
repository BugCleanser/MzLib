package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(void.class)
public interface VoidDelegator extends Delegator
{
	@Override
	Void getDelegate();
}
