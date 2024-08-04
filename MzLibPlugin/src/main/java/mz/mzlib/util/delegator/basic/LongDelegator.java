package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Long.class)
public interface LongDelegator extends Delegator
{
	static LongDelegator newInstance(long value)
	{
		return Delegator.create(LongDelegator.class,value);
	}
	
	@Override
	Long getDelegate();
	
	@Deprecated
	@DelegatorFieldAccessor("value")
	void setValue(long value);
}
