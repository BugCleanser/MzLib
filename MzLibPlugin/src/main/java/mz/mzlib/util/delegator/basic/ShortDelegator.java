package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(short.class)
public interface ShortDelegator extends Delegator
{
	@Override
	Short getDelegate();
}
