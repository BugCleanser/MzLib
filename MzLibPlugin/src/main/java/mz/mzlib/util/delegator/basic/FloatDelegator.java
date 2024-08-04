package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Float.class)
public interface FloatDelegator extends Delegator
{
	static FloatDelegator newInstance(float value)
	{
		return Delegator.create(FloatDelegator.class,value);
	}
	
	@Override
	Float getDelegate();
	
	@Deprecated
	@DelegatorFieldAccessor("value")
	void setValue(float value);
}
