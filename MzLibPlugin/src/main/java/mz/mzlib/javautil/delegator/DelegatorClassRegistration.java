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
	
	public DelegatorClassRegistration(Class<? extends Delegator> clazz)
	{
		this.clazz=clazz;
	}
	
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
	private MethodHandle constructor=null;
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
	private volatile MethodHandle volatileConstructor=null;
	public MethodHandle getConstructor()
	{
		MethodHandle con=constructor;
		if(con==null)
		{
			synchronized(this)
			{
				con=volatileConstructor;
				if(con==null)
				{
					
					//con=not null;
					volatileConstructor=con;
				}
				constructor=con;
			}
		}
		return con;
	}
}
