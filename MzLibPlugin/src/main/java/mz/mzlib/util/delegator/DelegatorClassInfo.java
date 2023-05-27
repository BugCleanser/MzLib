package mz.mzlib.util.delegator;

import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.WeakMap;
import mz.mzlib.util.WeakRef;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DelegatorClassInfo
{
	public Class<? extends Delegator> delegatorClass;
	public Map<Method,Member> delegations=new ConcurrentHashMap<>();
	public DelegatorClassInfo(Class<? extends Delegator> delegatorClass)
	{
		this.delegatorClass=delegatorClass;
		for(DelegatorClassAnalyzer i:DelegatorClassAnalyzerRegistrar.instance.analyzers.toArray(new DelegatorClassAnalyzer[0]))
			i.analyse(this);
	}
	
	public Class<? extends Delegator> getDelegatorClass()
	{
		return delegatorClass;
	}
	
	public static Map<Class<? extends Delegator>,WeakRef<DelegatorClassInfo>> cache=new WeakMap<>();
	public static DelegatorClassInfo get(Class<? extends Delegator> clazz)
	{
		return cache.computeIfAbsent(clazz,k->
		{
			DelegatorClassInfo result=new DelegatorClassInfo(clazz);
			ClassUtil.makeReference(clazz.getClassLoader(),result);
			return new WeakRef<>(result);
		}).get();
	}
	
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
	public MethodHandle constructorCache=null;
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
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
					genAClassAndPhuckTheJvm();
					result=constructorCache=constructor;
				}
			}
		}
		return result;
	}
	void genAClassAndPhuckTheJvm()
	{
	
	}
}
