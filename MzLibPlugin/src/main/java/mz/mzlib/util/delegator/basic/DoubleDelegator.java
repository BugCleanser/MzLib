package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Double.class)
public interface DoubleDelegator extends Delegator
{
	static DoubleDelegator newInstance(double value)
	{
		return Delegator.create(DoubleDelegator.class,value);
	}
	
	@Override
	Double getDelegate();
	
	@Deprecated
	@DelegatorFieldAccessor("value")
	void setValue(double value);
}
