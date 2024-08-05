package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(long.class)
public interface LongDelegator extends Delegator
{
	@Override
	Long getDelegate();
}
