package mz.mzlib.javautil;

import io.github.karlatemp.unsafeaccessor.Root;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

public class ClassUtil
{
	private ClassUtil() {}
	
	public static Method getDeclaredMethod(Class<?> clazz,Method method)
	{
		try
		{
			return clazz.getDeclaredMethod(method.getName(),method.getParameterTypes());
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	
	public static MethodHandle findMethod(Class<?> declaringClass,boolean isStatic,String name,Class<?> returnType,Class<?> ...parameterTypes)
	{
		try
		{
			if(isStatic)
				return Root.getTrusted(declaringClass).findStatic(declaringClass,name,MethodType.methodType(returnType,parameterTypes));
			else
				return Root.getTrusted(declaringClass).findVirtual(declaringClass,name,MethodType.methodType(returnType,parameterTypes));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	public static MethodHandle findMethodSpecial(Class<?> declaringClass,boolean isStatic,String name,Class<?> returnType,Class<?> ...parameterTypes)
	{
		try
		{
			return Root.getTrusted(declaringClass).findSpecial(declaringClass,name,MethodType.methodType(returnType,parameterTypes),declaringClass);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	
	public static MethodHandle unreflect(Constructor<?> constructor)
	{
		try
		{
			return Root.getTrusted(constructor.getDeclaringClass()).unreflectConstructor(constructor);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	public static MethodHandle unreflect(Method method)
	{
		try
		{
			return Root.getTrusted(method.getDeclaringClass()).unreflect(method);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	public static MethodHandle unreflectSpecial(Method method)
	{
		try
		{
			return Root.getTrusted(method.getDeclaringClass()).unreflectSpecial(method,method.getDeclaringClass());
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	
	public static void forEachSuper(Class<?> clazz,Consumer<Class<?>> proc)
	{
		proc.accept(clazz);
		if(clazz!=Object.class)
			forEachSuper(clazz.getSuperclass(),proc);
		for(Class<?> i:clazz.getInterfaces())
			forEachSuper(i,proc);
	}
	public static void forEachSuperUnique(Class<?> clazz,Consumer<Class<?>> proc)
	{
		Set<Class<?>> history=new HashSet<>();
		forEachSuper(clazz,c->
		{
			if(history.add(c))
				proc.accept(c);
		});
	}
	
	/**
	 * Iterate through all super classes in topological order
	 * From super to children
	 */
	public static void forEachSuperTopology(Class<?> clazz,Consumer<Class<?>> proc)
	{
		Map<Class<?>,Integer> degreeIn=new HashMap<>();
		Map<Class<?>,Set<Class<?>>> edgeOut=new HashMap<>();
		forEachSuperUnique(clazz,c->
		{
			if(c!=Object.class)
			{
				edgeOut.computeIfAbsent(c.getSuperclass(),k->new HashSet<>()).add(c);
				degreeIn.compute(c,(k,v)->(v!=null?v:0)+1);
			}
			for(Class<?> i:clazz.getInterfaces())
				edgeOut.computeIfAbsent(i,k->new HashSet<>()).add(c);
			degreeIn.compute(c,(k,v)->(v!=null?v:0)+clazz.getInterfaces().length);
		});
		Queue<Class<?>> q=new ArrayDeque<>();
		q.add(Object.class);
		while(!q.isEmpty())
		{
			Class<?> now=q.poll();
			proc.accept(now);
			Set<Class<?>> es=edgeOut.get(now);
			if(es!=null)
				for(Class<?> c:es)
					if(degreeIn.compute(c,(k,v)->Objects.requireNonNull(v)-1)==0)
						q.add(c);
		}
	}
}
