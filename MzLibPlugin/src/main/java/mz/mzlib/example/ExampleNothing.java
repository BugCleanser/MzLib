package mz.mzlib.example;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorMethod;
import mz.mzlib.util.delegator.DelegatorSameClass;
import mz.mzlib.util.delegator.basic.Delegator_double;
import mz.mzlib.util.delegator.basic.Delegator_void;
import mz.mzlib.util.nothing.*;


@Deprecated
public class ExampleNothing
{
    public static class Foo
    {
        private void a()
        {
        }

        public void f()
        {
            System.out.println(3.14);
        }

        public void g()
        {
            System.out.println("Hello World");
        }
    }

    @DelegatorClass(Foo.class)
    public interface DelegatorFoo extends Delegator
    {
        @DelegatorMethod("f")
        void f();

        @DelegatorMethod("g")
        void g();
    }

    @DelegatorSameClass(DelegatorFoo.class)
    public interface NothingFoo extends DelegatorFoo, Nothing
    {
        @NothingInject(delegatorMethod = "f", locatingSteps = {@LocatingStep(type = LocatingStepType.AFTER_FIRST, arg = Opcodes.INVOKEVIRTUAL)}, type = NothingInjectType.INSERT_BEFORE)
        default Delegator_void injectF(@StackTop Delegator_double s)
        {
            this.g();
            return Delegator_void.create(null);
        }
    }

    public static void main(String[] args)
    {
        NothingClassRegistrar.instance.register(null, NothingFoo.class);
        new Foo().f();
    }
}
