package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(long.class)
public interface Delegator_long extends Delegator
{
	@Override
	Long getDelegate();
}
