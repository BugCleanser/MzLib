package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(Integer.class)
public interface Delegator_int extends Delegator
{
	@Override
	Integer getDelegate();
}
