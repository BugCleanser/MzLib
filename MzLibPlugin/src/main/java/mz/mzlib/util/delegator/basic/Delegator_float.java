package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(float.class)
public interface Delegator_float extends Delegator
{
	@Override
	Float getDelegate();
}
