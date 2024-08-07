package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Long.class)
public interface DelegatorLong extends Delegator
{
	@Override
	Long getDelegate();
	
	@DelegatorFieldAccessor("value")
	void setValue(long value);
}
