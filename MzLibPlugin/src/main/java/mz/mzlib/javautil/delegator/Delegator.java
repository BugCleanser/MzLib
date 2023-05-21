package mz.mzlib.javautil.delegator;

public interface Delegator
{
	Object getDelegate();
	void setDelegate(Object delegate);
	
	Delegator clone0();
}
