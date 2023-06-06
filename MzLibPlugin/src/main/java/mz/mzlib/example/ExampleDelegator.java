package mz.mzlib.example;

import mz.mzlib.MzLib;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

public class ExampleDelegator
{
	public static final double i=114.514;
	
	@DelegatorClass(ExampleDelegator.class)
	public interface TestDelegator extends Delegator
	{
		@DelegatorFieldAccessor("i")
		double getI();
		@DelegatorFieldAccessor("i")
		void setI(double i);
	}
	
	public static void main(String[] args)
	{
		MzLib.instance.load();
		TestDelegator t=Delegator.create(TestDelegator.class,new ExampleDelegator());
		System.out.println(t.getI());
		t.setI(191981.0);
		System.out.println(t.getI());
	}
}