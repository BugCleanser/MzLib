package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Float.class)
public interface DelegatorFloat extends Delegator
{
	@Override
	Float getDelegate();
	
	@DelegatorFieldAccessor("value")
	void setValue(float value);
}
