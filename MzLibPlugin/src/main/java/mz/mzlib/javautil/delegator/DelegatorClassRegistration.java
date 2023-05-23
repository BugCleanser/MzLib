package mz.mzlib.javautil.delegator;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DelegatorClassRegistration
{
	public Class<? extends Delegator> clazz;
	public Class<?> delegateClass=null;
	public Map<Method,Member> delegateMembers=new HashMap<>();
	public volatile MethodHandle constructor=null;
	
	public DelegatorClassRegistration(Class<? extends Delegator> clazz)
	{
		this.clazz=clazz;
	}
	
	public MethodHandle getConstructor()
	{
		MethodHandle con=constructor;
		if(con==null)
		{
			synchronized(this)
			{
				con=constructor;
				if(con==null)
				{
					
					//con=constructor=not null;
				}
			}
		}
		return con;
	}
}
