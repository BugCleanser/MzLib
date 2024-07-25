package mz.mzlib.example;

import java.lang.invoke.MethodHandles;

public class Test
{
	public static void main(String[] args) throws Throwable
	{
		System.out.println((String)MethodHandles.constant(String.class,"awa").invokeExact());
	}
}
