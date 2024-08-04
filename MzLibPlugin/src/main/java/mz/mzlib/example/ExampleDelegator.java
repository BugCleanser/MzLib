package mz.mzlib.example;

import mz.mzlib.MzLib;
import mz.mzlib.util.delegator.*;

@Deprecated
public class ExampleDelegator
{
	public static class Test implements Cloneable
	{
		private final double var=114.514;
		private final Test thiz=this;
		private Test()
		{
		}
		private void m()
		{
			System.out.println("HelloWorld");
		}
		private static void m1()
		{
			System.out.println("HelloWorld1");
		}
	}
	
	@DelegatorClass(Test.class)
	public interface TestDelegator extends Delegator
	{
		@DelegatorConstructor
		TestDelegator staticNewInstance();
		static TestDelegator newInstace()
		{
			return Delegator.createStatic(TestDelegator.class).staticNewInstance();
		}
		
		@DelegatorFieldAccessor("var")
		double getVar();
		@DelegatorFieldAccessor("var")
		void setVar(double var);
		
		@DelegatorFieldAccessor("thiz")
		TestDelegator getThiz();
		@DelegatorFieldAccessor("@0")
		TestDelegator ditto();
		
		@DelegatorMethod("m")
		void m();
		@DelegatorMethod("m1")
		void staticM1();
		static void m1()
		{
			Delegator.createStatic(TestDelegator.class).staticM1();
		}
	}
	
	public static void main(String[] args)
	{
		MzLib.instance.load();
		
		TestDelegator t=TestDelegator.newInstace();
		System.out.println(t.getVar());
		t=t.getThiz();
		t.setVar(191981.0);
		System.out.println(t.getVar());
		t=((AbsDelegator)t).clone().cast(TestDelegator.class);
		t.m();
		TestDelegator.m1();
		
		t=Delegator.allocateInstance(TestDelegator.class);
		System.out.println(t.getVar());
	}
}