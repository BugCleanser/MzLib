package mz.mzlib.example;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.basic.Wrapper_double;
import mz.mzlib.util.wrapper.basic.Wrapper_void;
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

    @WrapClass(Foo.class)
    public interface WrapperFoo extends WrapperObject
    {
        @WrapMethod("f")
        void f();

        @WrapMethod("g")
        void g();
    }

    @WrapSameClass(WrapperFoo.class)
    public interface NothingFoo extends WrapperFoo, Nothing
    {
        @NothingInject(wrapperMethod = "f", locatingSteps = {@LocatingStep(type = LocatingStepType.AFTER_FIRST, arg = Opcodes.INVOKEVIRTUAL)}, type = NothingInjectType.INSERT_BEFORE)
        default Wrapper_void injectF(@StackTop Wrapper_double s)
        {
            this.g();
            return Wrapper_void.create(null);
        }
    }

    public static void main(String[] args)
    {
        NothingClassRegistrar.instance.register(null, NothingFoo.class);
        new Foo().f();
    }
}
