package mz.mzlib.util.delegator;

import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.WeakMap;
import mz.mzlib.util.WeakRef;

import java.lang.invoke.MethodHandle;
import java.util.Map;

public class DelegatorClassInfo
{
	public static Map<Class<? extends Delegator>,WeakRef<DelegatorClassInfo>> cache=new WeakMap<>();
	
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
			ClassUtil.makeReference(clazz,result);
			return new WeakRef<>(result);
		}).get();
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
