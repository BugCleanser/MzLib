package mz.mzlib.example;

import io.github.karlatemp.unsafeaccessor.Root;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class Test
{
	public static MethodHandle UNREFLECT_GETTER(Field field) throws NoSuchMethodException, IllegalAccessException
	{
		return Root.getTrusted(Field.class).findVirtual(Field.class,"get",MethodType.methodType(Object.class,Object.class)).bindTo(field);
	}
	
	public static void main(String[] args) throws Throwable
	{
	
	}
}
