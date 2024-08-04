package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;

@DelegatorClass(String.class)
public interface StringDelegator extends Delegator
{
	String getDelegate();
}
