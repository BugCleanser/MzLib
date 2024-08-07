package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(double.class)
public interface Delegator_double extends Delegator
{
	@Override
	Double getDelegate();
}
