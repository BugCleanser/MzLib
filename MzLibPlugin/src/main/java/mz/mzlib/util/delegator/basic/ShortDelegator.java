package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Short.class)
public interface ShortDelegator extends Delegator
{
	static ShortDelegator newInstance(short value)
	{
		return Delegator.create(ShortDelegator.class,value);
	}
	
	@Override
	Short getDelegate();
	
	@Deprecated
	@DelegatorFieldAccessor("value")
	void setValue(short value);
}
