package mz.mzlib.javautil.delegator;

import java.lang.invoke.*;

public class DelegatorClassRegistration
{
	public MethodHandle constructor=null;
	public DelegatorClassRegistration(Class<? extends Delegator> clazz)
	{
	
	}
	public MethodHandle getConstructor()
	{
		return constructor;
	}
}
