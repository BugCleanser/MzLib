package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Integer.class)
public interface IntegerDelegator extends Delegator
{
	static IntegerDelegator newInstance(int value)
	{
		return Delegator.create(IntegerDelegator.class,value);
	}
	
	@Override
	Integer getDelegate();
	
	@Deprecated
	@DelegatorFieldAccessor("value")
	void setValue(int value);
}
