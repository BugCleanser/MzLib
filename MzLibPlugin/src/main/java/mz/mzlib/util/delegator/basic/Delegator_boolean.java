package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(boolean.class)
public interface Delegator_boolean extends Delegator
{
	@Override
	Boolean getDelegate();
}
