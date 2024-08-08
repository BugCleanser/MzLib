package mz.mzlib.example;

import mz.mzlib.util.delegator.*;

@Deprecated
public class ExampleDelegator
{
	@SuppressWarnings("all")
	public static class Test implements Cloneable
	{
		private double var=114.514;
		private Test thiz=this;
		
		private Test()
		{
		}
		
		private void m()
		{
			System.out.println(thiz.var);
		}
		
		private static void m1()
		{
			System.out.println("HelloWorld");
		}
	}
	
	public static void main(String[] args)
	{
		TestDelegator.m1();
		TestDelegator t=TestDelegator.newInstance();
		t.m();
		t.setVar(1919.810);
		t.m();
	}
	
	public interface TestDelegator extends Delegator
	{
		@DelegatorCreator
		static TestDelegator create(Object delegate)
		{
			return Delegator.create(TestDelegator.class,delegate);
		}
		
		@DelegatorConstructor
		TestDelegator staticNewInstance();
		static TestDelegator newInstance()
		{
			return create(null).staticNewInstance();
		}
		
		@DelegatorMethod("m")
		void m();
		@DelegatorMethod("m1")
		void staticM1();
		static void m1()
		{
			create(null).staticM1();
		}
		@DelegatorFieldAccessor("var")
		void setVar(double var);
	}
	
}