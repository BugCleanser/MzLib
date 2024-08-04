package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorConstructor;

@DelegatorClass(Void.class)
public interface VoidDelegator extends Delegator
{
	static VoidDelegator newInstance()
	{
		return Delegator.createStatic(VoidDelegator.class).staticNewInstance();
	}
	@DelegatorConstructor
	VoidDelegator staticNewInstance();
	
	@Override
	Void getDelegate();
}
