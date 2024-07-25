package mz.mzlib.example;

import mz.mzlib.util.ClassUtil;
import net.bytebuddy.agent.ByteBuddyAgent;

import java.lang.invoke.MethodHandles;

public class Test
{
	public static void main(String[] args) throws Throwable
	{
		ByteBuddyAgent.install();
		ByteBuddyAgent.install();
		ByteBuddyAgent.install();
		ClassUtil.getByteCode(Test.class);
		System.out.println((String)MethodHandles.constant(String.class,"awa").invokeExact());
	}
}
