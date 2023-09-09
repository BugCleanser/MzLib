package mz.mzlib.util.delegator;

import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class DefaultDelegatorClassAnalyzer implements SimpleDelegatorClassAnalyzer,Instance
{
	public static DefaultDelegatorClassAnalyzer instance=new DefaultDelegatorClassAnalyzer();
	
	@Override
	public Class<?> analyseClass(ClassLoader classLoader,Annotation annotation)
	{
		if(annotation instanceof DelegatorClass)
			return ((DelegatorClass)annotation).value();
		else if(annotation instanceof DelegatorSameClass)
			return DelegatorClassInfo.get(((DelegatorSameClass)annotation).value()).getDelegateClass();
		else if(annotation instanceof DelegatorClassForName)
		
		{
			Throwable lastException=null;
			for(String i:((DelegatorClassForName)annotation).value())
			{
				try
				{
					return Class.forName(i,false,classLoader);
				}
				catch(Throwable e)
				{
					lastException=e;
				}
			}
			throw RuntimeUtil.sneakilyThrow(lastException);
		}
		return null;
	}
	
	@Override
	public Member analyseMember(Class<?> delegateClass,Annotation annotation,Class<?> returnType,Class<?>[] argTypes)
	{
		try
		{
			if(annotation instanceof DelegatorConstructor)
			{
				return delegateClass.getDeclaredConstructor(argTypes);
			}
			else if(annotation instanceof DelegatorMethod)
			{
				Throwable lastException=null;
				for(String i: ((DelegatorMethod)annotation).value())
				{
					try
					{
						return delegateClass.getDeclaredMethod(i,argTypes);
					}
					catch(Throwable e)
					{
						lastException=e;
					}
				}
				throw RuntimeUtil.sneakilyThrow(lastException);
			}
			else if(annotation instanceof DelegatorFieldAccessor)
			{
				Class<?> type;
				switch(argTypes.length)
				{
					case 0:
						type=returnType;
						break;
					case 1:
						type=argTypes[0];
						break;
					default:
						throw new IllegalArgumentException("Too many args: "+Arrays.toString(argTypes)+".");
				}
				Throwable lastException=null;
				for(String i:((DelegatorFieldAccessor)annotation).value())
				{
					try
					{
						if(i.startsWith("#") || i.startsWith("@"))
							return Arrays.stream(delegateClass.getDeclaredFields()).filter(j->j.getType()==type && (Modifier.isStatic(j.getModifiers())^i.startsWith("@"))).toArray(Field[]::new)[Integer.parseInt(i.substring(1))];
						else
							return RuntimeUtil.require(delegateClass.getDeclaredField(i),f->f.getType()==type);
					}
					catch(Throwable e)
					{
						lastException=e;
					}
				}
				throw RuntimeUtil.sneakilyThrow(lastException);
			}
			return null;
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
}
