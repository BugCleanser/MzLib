package mz.lib;

import mz.lib.wrapper.WrappedObject;
import org.apache.logging.log4j.util.TriConsumer;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.function.BiFunction;

public final class TypeUtil
{
	public @Deprecated
	TypeUtil()
	{
	}
	public static RuntimeException throwException(final Throwable e)
	{
		TypeUtil.throwException0(e);
		return TypeUtil.cast(null);
	}
	@SuppressWarnings("unchecked")
	public static <E extends Throwable> void throwException0(final Throwable e) throws E
	{
		throw (E) e;
	}
	@SuppressWarnings("unchecked")
	public static <T,O> T cast(O o)
	{
		return (T) o;
	}
	public static boolean hasThrowable(Runnable runnable)
	{
		return getThrowable(runnable)!=null;
	}
	public static Throwable getThrowable(Runnable runnable)
	{
		try
		{
			runnable.run();
		}
		catch(Throwable e)
		{
			return e;
		}
		return null;
	}
	public static void run(Runnable func)
	{
		try
		{
			func.run();
		}
		catch(Throwable e)
		{
			throw throwException(e);
		}
	}
	public static <T> T run(Function<T> func)
	{
		try
		{
			return func.run();
		}
		catch(Throwable e)
		{
			throw throwException(e);
		}
	}
	public static <T> T value(T value)
	{
		return value;
	}
	public static <T extends Throwable> void fakeThrow() throws T
	{
	}
	public static <T extends Throwable> void fakeThrow(Class<T> throwable) throws T
	{
	}
	public static String throwableToString(Throwable e)
	{
		java.io.ByteArrayOutputStream m=new java.io.ByteArrayOutputStream();
		java.io.PrintStream p=null;
		try
		{
			p=new java.io.PrintStream(m,true,"UTF8");
		}
		catch(Throwable e2)
		{
			throwException(e2);
		}
		e.printStackTrace(p);
		return (e instanceof Error?"§4":"§e")+new String(m.toByteArray(),StandardCharsets.UTF_8).replace("\r","").replace("\t","  ");
	}
	@Deprecated
	public static String codeCast(Class<?> tar,String valueCode)
	{
		return codeCast(tar,valueCode,Object.class);
	}
	public static String codeCast(Class<?> tar,String valueCode,Class<?> valueType)
	{
		return codeCast(tar,valueCode,valueType,true);
	}
	public static String codeCast(Class<?> tar,String valueCode,Class<?> valueType,boolean autoWrapObject)
	{
		if(tar==valueType)
			return valueCode;
		if(autoWrapObject)
		{
			if(WrappedObject.class.isAssignableFrom(valueType))
			{
				valueCode="("+valueCode+").getRaw()";
				valueType=Object.class;
			}
			if(WrappedObject.class.isAssignableFrom(tar))
			{
				valueCode=ClassUtil.getName(WrappedObject.class)+".wrap("+ClassUtil.getName(tar)+".class,"+codeCast(Object.class,valueCode,valueType)+")";
				valueType=tar;
			}
		}
		if(tar.isPrimitive()&&!valueType.isPrimitive())
		{
			if(tar==boolean.class)
				return "((Boolean)"+valueCode+").booleanValue()";
			else if(tar==char.class)
				return "((Character)"+valueCode+").charValue()";
			else if(tar==byte.class)
				return "((Byte)"+valueCode+").byteValue()";
			else if(tar==short.class)
				return "((Short)"+valueCode+").shortValue()";
			else if(tar==int.class)
				return "((Integer)"+valueCode+").intValue()";
			else if(tar==long.class)
				return "((Long)"+valueCode+").longValue()";
			else if(tar==float.class)
				return "((Float)"+valueCode+").floatValue()";
			else if(tar==double.class)
				return "((Double)"+valueCode+").doubleValue()";
			else if(tar==void.class)
				return "";
			throw new IllegalArgumentException();
		}
		else if(valueType.isPrimitive()&&!tar.isPrimitive())
		{
			if(valueType==boolean.class)
				return "Boolean.valueOf("+valueCode+")";
			else if(valueType==char.class)
				return "Character.valueOf("+valueCode+")";
			else if(valueType==byte.class)
				return "Byte.valueOf("+valueCode+")";
			else if(valueType==short.class)
				return "Short.valueOf("+valueCode+")";
			else if(valueType==int.class)
				return "Integer.valueOf("+valueCode+")";
			else if(valueType==long.class)
				return "Long.valueOf("+valueCode+")";
			else if(valueType==float.class)
				return "Float.valueOf("+valueCode+")";
			else if(valueType==double.class)
				return "Double.valueOf("+valueCode+")";
			else if(valueType==void.class)
				return "null";
			throw new IllegalArgumentException();
		}
		else if(!(tar.isPrimitive()&&valueType.isPrimitive()))
			return "(("+ClassUtil.getName(tar)+")"+valueCode+")";
		else
			return "";
	}
	
	public static String codeArgs(int length)
	{
		String[] args=new String[length];
		for(int i=0;i<args.length;i++)
			args[i]="$"+(i+1);
		return StringUtil.mergeStrings(",",args);
	}
	public static String codeArgObjects(Class[] argTypes)
	{
		String[] args=new String[argTypes.length];
		for(int i=0;i<args.length;i++)
			args[i]=codeCast(Object.class,"$"+(i+1),argTypes[i]);
		return StringUtil.mergeStrings(",",args);
	}
	public static String codeArgsCast(Class[] target,Class[] source)
	{
		String[] args=new String[target.length];
		for(int i=0;i<args.length;i++)
			args[i]=TypeUtil.codeCast(target[i],"$"+(i+1),source[i]);
		return StringUtil.mergeStrings(",",args);
	}
	
	public static <T> Class<T> toWrapper(Class<T> clazz)
	{
		if(clazz.isPrimitive())
		{
			if(clazz==byte.class)
				return (Class<T>) Byte.class;
			else if(clazz==short.class)
				return (Class<T>) Short.class;
			else if(clazz==int.class)
				return (Class<T>) Integer.class;
			else if(clazz==long.class)
				return (Class<T>) Long.class;
			else if(clazz==float.class)
				return (Class<T>) Float.class;
			else if(clazz==double.class)
				return (Class<T>) Double.class;
			else if(clazz==boolean.class)
				return (Class<T>) Boolean.class;
			else if(clazz==char.class)
				return (Class<T>) Character.class;
		}
		return clazz;
	}
	public static Class<?>[] toWrapper(Class<?>[] clazz)
	{
		Class<?>[] r=new Class[clazz.length];
		for(int i=0;i<r.length;i++)
			r[i]=toWrapper(clazz[i]);
		return r;
	}
	public static <T> Class<T> toPrimitive(Class<T> clazz)
	{
		if(Serializable.class.isAssignableFrom(clazz))
		{
			if(Number.class.isAssignableFrom(clazz))
			{
				if(clazz==Integer.class)
					return (Class<T>) int.class;
				else if(clazz==Float.class)
					return (Class<T>) float.class;
				else if(clazz==Long.class)
					return (Class<T>) long.class;
				else if(clazz==Short.class)
					return (Class<T>) short.class;
				else if(clazz==Double.class)
					return (Class<T>) double.class;
				else if(clazz==Byte.class)
					return (Class<T>) byte.class;
			}
			else if(clazz==Boolean.class)
				return (Class<T>) boolean.class;
			else if(clazz==Character.class)
				return (Class<T>) char.class;
		}
		return clazz;
	}
	
	public static Object[] toWrapperArray(Object array)
	{
		Class<?> componentType=array.getClass().getComponentType();
		if(componentType.isPrimitive())
		{
			BiFunction<Object,Integer,Object> getter=null;
			if(componentType==byte.class)
				getter=Array::getByte;
			else if(componentType==short.class)
				getter=Array::getShort;
			else if(componentType==int.class)
				getter=Array::getInt;
			else if(componentType==long.class)
				getter=Array::getLong;
			else if(componentType==float.class)
				getter=Array::getFloat;
			else if(componentType==double.class)
				getter=Array::getDouble;
			else if(componentType==boolean.class)
				getter=Array::getBoolean;
			else if(componentType==char.class)
				getter=Array::getChar;
			
			Object[] r=TypeUtil.cast(Array.newInstance(toWrapper(componentType),Array.getLength(array)));
			for(int i=0;i<r.length;i++)
			{
				r[i]=getter.apply(array,i);
			}
			return r;
		}
		return TypeUtil.cast(array);
	}
	public static Object toPrimitiveArray(Object array)
	{
		Class<?> componentType=array.getClass().getComponentType();
		if(!componentType.isPrimitive()&&toPrimitive(componentType).isPrimitive())
		{
			TriConsumer<Object,Integer,Object> setter=null;
			if(Number.class.isAssignableFrom(componentType))
			{
				if(componentType==Byte.class)
					setter=(a,i,v)->((byte[])a)[i]=(Byte) v;
				else if(componentType==Short.class)
					setter=(a,i,v)->((short[])a)[i]=(Short) v;
				else if(componentType==Integer.class)
					setter=(a,i,v)->((int[])a)[i]=(Integer) v;
				else if(componentType==Long.class)
					setter=(a,i,v)->((long[])a)[i]=(Long) v;
				else if(componentType==Float.class)
					setter=(a,i,v)->((float[])a)[i]=(Float) v;
				else if(componentType==Double.class)
					setter=(a,i,v)->((double[])a)[i]=(Double) v;
			}
			else if(componentType==Boolean.class)
				setter=(a,i,v)->((boolean[])a)[i]=(Boolean) v;
			else if(componentType==Character.class)
				setter=(a,i,v)->((char[])a)[i]=(Character) v;
			
			Object r=Array.newInstance(toPrimitive(componentType),((Object[])array).length);
			for(int i=0;i<((Object[])array).length;i++)
			{
				setter.accept(r,i,((Object[])array)[i]);
			}
			return r;
		}
		return array;
	}
	
	public static <T> T debug(T obj)
	{
		System.err.println("Debug: "+obj);
		return obj;
	}
	
	public interface Function<T>
	{
		T run() throws Throwable;
	}
	public interface Runnable
	{
		void run() throws Throwable;
	}
}
