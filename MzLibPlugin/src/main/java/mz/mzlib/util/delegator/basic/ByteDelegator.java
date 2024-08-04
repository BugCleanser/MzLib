package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Byte.class)
public interface ByteDelegator extends Delegator
{
	static ByteDelegator newInstance(byte value)
	{
		return Delegator.create(ByteDelegator.class,value);
	}
	
	@Override
	Byte getDelegate();
	
	@Deprecated
	@DelegatorFieldAccessor("value")
	void setValue(byte value);
}
