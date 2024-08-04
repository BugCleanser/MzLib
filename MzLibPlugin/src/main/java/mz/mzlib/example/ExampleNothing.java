package mz.mzlib.example;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorMethod;
import mz.mzlib.util.delegator.basic.StringDelegator;
import mz.mzlib.util.delegator.basic.VoidDelegator;
import mz.mzlib.util.nothing.*;

public class ExampleNothing
{
	public static class Foo
	{
		public void f()
		{
			System.out.println("Hello World");
		}
	}
	@DelegatorClass(Foo.class)
	public interface NothingFoo extends Delegator,Nothing
	{
		@DelegatorMethod("f")
		void staticF();
		@NothingInject(delegatorMethod="staticF",locatingSteps={@LocatingStep(type=LocatingStepType.AFTER_FIRST,arg= Opcodes.INVOKEVIRTUAL)},type=NothingInjectType.INSERT_BEFORE)
		default VoidDelegator injectF(@StackTop StringDelegator s)
		{
			s.setDelegate("awa");
			return Nothing.notReturn();
		}
	}
	public static void main(String[] args)
	{
		NothingClassRegistrar.instance.register(null,NothingFoo.class);
		new Foo().f();
	}
}
