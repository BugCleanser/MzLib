package mz.mzlib.example;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorMethod;
import mz.mzlib.util.delegator.basic.DoubleDelegator;
import mz.mzlib.util.delegator.basic.VoidDelegator;
import mz.mzlib.util.nothing.*;

@Deprecated
public class ExampleNothing
{
	public static class Foo
	{
		public void f()
		{
			System.out.println(3.14);
		}
	}
	@DelegatorClass(Foo.class)
	public interface NothingFoo extends Delegator,Nothing
	{
		@DelegatorMethod("f")
		void staticF();
		@NothingInject(delegatorMethod="staticF",locatingSteps={@LocatingStep(type=LocatingStepType.AFTER_FIRST,arg= Opcodes.INVOKEVIRTUAL)},type=NothingInjectType.INSERT_BEFORE)
		default VoidDelegator injectF(@StackTop DoubleDelegator s)
		{
			s.setDelegate(114.514);
			return Nothing.notReturn();
		}
	}
	public static void main(String[] args)
	{
		NothingClassRegistrar.instance.register(null,NothingFoo.class);
		new Foo().f();
	}
}
