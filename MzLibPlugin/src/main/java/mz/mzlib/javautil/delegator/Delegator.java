package mz.mzlib.javautil.delegator;

public interface Delegator
{
	Object getDelegate();
	void setDelegate(Object delegate);
	
	@DelegationMethod("clone")
	Delegator clone0();
}
