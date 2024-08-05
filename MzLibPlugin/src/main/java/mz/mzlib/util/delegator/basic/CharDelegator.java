package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(char.class)
public interface CharDelegator extends Delegator
{
	@Override
	Character getDelegate();
}
