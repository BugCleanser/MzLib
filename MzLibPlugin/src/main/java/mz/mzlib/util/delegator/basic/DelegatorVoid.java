package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(Void.class)
public interface DelegatorVoid extends Delegator
{
	@Override
	Void getDelegate();
}
