package mz.mzlib.example;

import mz.mzlib.util.ClassUtil;

public class Test
{
	private static class Foo
	{
		private Foo()
		{
		}
	}
	
	public static void main(String[] args) throws Throwable
	{
		System.out.println((Foo)ClassUtil.findConstructor(Foo.class).invokeExact());
	}
}
