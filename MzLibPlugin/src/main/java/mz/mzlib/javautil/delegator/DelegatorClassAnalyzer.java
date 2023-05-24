package mz.mzlib.javautil.delegator;

public abstract class DelegatorClassAnalyzer
{
	public abstract void analyse(Class<? extends Delegator> clazz,DelegatorClassInfo info);
}
