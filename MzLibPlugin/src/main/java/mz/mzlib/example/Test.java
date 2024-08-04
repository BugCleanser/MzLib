package mz.mzlib.example;

import mz.mzlib.util.delegator.basic.VoidDelegator;

@Deprecated
public class Test
{
	public static void main(String[] args) throws Throwable
	{
		Void v=VoidDelegator.newInstance().getDelegate();
		System.out.println(v);
	}
}
