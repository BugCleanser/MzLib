package mz.mzlib.javautil;

import io.github.karlatemp.unsafeaccessor.Root;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public class ClassUtil
{
	private ClassUtil() {}
	
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
		throw new UnsupportedOperationException();
	}
}
