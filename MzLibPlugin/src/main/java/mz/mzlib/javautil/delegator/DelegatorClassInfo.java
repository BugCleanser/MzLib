package mz.mzlib.javautil.delegator;

import mz.mzlib.javautil.WeakMap;

import java.lang.invoke.MethodHandle;
import java.util.Map;

public class DelegatorClassInfo
{
	public static Map<Class<? extends Delegator>,DelegatorClassInfo> cache=new WeakMap<>();
	
	@Deprecated
	@SuppressWarnings("DeprecatedIsStillUsed")
	public DelegatorClassInfo()
	{
	}
	
	public static DelegatorClassInfo get(Class<? extends Delegator> clazz)
	{
		return cache.computeIfAbsent(clazz,k->
		{
			DelegatorClassInfo result=new DelegatorClassInfo();
			for(DelegatorClassAnalyzer i:DelegatorClassAnalyzerRegistrar.instance.analyzers)
				i.analyse(k,result);
			return result;
		});
	}
	
	@Deprecated
	@SuppressWarnings("DeprecatedIsStillUsed")
	public MethodHandle constructorCache=null;
	@Deprecated
	@SuppressWarnings("DeprecatedIsStillUsed")
	public volatile MethodHandle constructor=null;
	public MethodHandle getConstructor()
	{
		MethodHandle result=constructorCache;
		if(result==null)
		{
			synchronized(this)
			{
				result=constructorCache=constructor;
				if(result==null)
				{
					
					result=constructorCache=constructor;
				}
			}
		}
		return result;
	}
}
