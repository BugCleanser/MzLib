package mz.lib;

import com.google.common.collect.Lists;
import io.github.karlatemp.unsafeaccessor.Root;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class Function
{
	public @Deprecated
	Executable e;
	public @Deprecated
	MethodHandle mh;
	
	public Function(Executable e)
	{
		this.e=e;
		Root.setAccessible(e,true);
		try
		{
			if(e instanceof Method)
			{
				mh=Root.getTrusted().unreflect((Method) e);
			}
			else
			{
				mh=Root.getTrusted().unreflectConstructor((Constructor<?>) e);
			}
		}
		catch(Throwable e1)
		{
			throw TypeUtil.throwException(e1);
		}
	}
	public static List<Function> getFunctions(Class<?> clazz)
	{
		List<Function> r=Lists.newArrayList(clazz.getDeclaredConstructors()).stream().map(Function::new).collect(Collectors.toList());
		r.addAll(Lists.newArrayList(clazz.getDeclaredMethods()).stream().map(Function::new).collect(Collectors.toList()));
		return r;
	}
	public Executable getExecutable()
	{
		return e;
	}
	public Object invoke(List<?> args)
	{
		try
		{
			return mh.invokeWithArguments(args);
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public String getName()
	{
		if(e instanceof Constructor)
			return "<init>";
		return e.getName();
	}
}
