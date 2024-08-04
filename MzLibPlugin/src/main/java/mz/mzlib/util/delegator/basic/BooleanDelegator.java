package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Boolean.class)
public interface BooleanDelegator extends Delegator
{
	static BooleanDelegator newInstance(boolean value)
	{
		return Delegator.create(BooleanDelegator.class,value);
	}
	
	@Override
	Boolean getDelegate();
	
	@Deprecated
	@DelegatorFieldAccessor("value")
	void setValue(boolean value);
}
