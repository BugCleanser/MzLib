package mz.mzlib.util.delegator;

public abstract class DelegatorClassAnalyzer
{
	public abstract void analyse(Class<? extends Delegator> clazz,DelegatorClassInfo info);
}
