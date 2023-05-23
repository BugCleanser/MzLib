package mz.mzlib.javautil.delegator;

import java.lang.invoke.*;
import java.lang.reflect.*;

public class DelegatorClassRegistration
{
	public Class<? extends Delegator> clazz;
	public volatile MethodHandle constructor=null;
	
	public DelegatorClassRegistration(Class<? extends Delegator> clazz)
	{
		this.clazz=clazz;
	}
	
	public MethodHandle getConstructor()
	{
		if(constructor==null)
		{
			synchronized(this)
			{
				if(constructor==null)
				{
					constructor=new MethodHandle();
					Proxy.newProxyInstance()
				}
			}
		}
		return constructor;
	}
}
