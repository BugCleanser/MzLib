package mz.mzlib.example;

import mz.mzlib.MzLib;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.*;
import mz.mzlib.util.wrapper.basic.Wrapper_double;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

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
            // println
            // ..., System.out, 3.14
        }

        public void g()
        {
            System.out.println("Hello World");
        }
    }

    @WrapClass(Foo.class)
    public interface WrapperFoo extends WrapperObject
    {
        @WrapperCreator
        static WrapperFoo create(Object wrapped)
        {
            return WrapperObject.create(WrapperFoo.class, wrapped);
        }

        @WrapMethod("f")
        void f123();

        @WrapMethod("g")
        void g();
    }

    @WrapSameClass(WrapperFoo.class)
    public interface NothingFoo extends WrapperFoo, Nothing
    {
        static void injectionFLocate(NothingInjectLocating locating)
        {
            locating.next(Opcodes.INVOKEVIRTUAL);
//            locating.next(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, AsmUtil.getType(PrintStream.class), "println", AsmUtil.getDesc(void.class, double.class), false));
            assert locating.locations.size()==1;
        }
        @NothingInject(wrapperMethod = "f123", locateMethod = "injectionFLocate", type = NothingInjectType.INSERT_BEFORE)
        default Wrapper_void injectionF(@StackTop Wrapper_double s)
        {
            s.setWrapped(1.14);
            this.g();
            return Nothing.notReturn();
        }
    }

    public static void main(String[] args)
    {
        MzLib.instance.load();

        new Foo().f();
        MzLib.instance.register(NothingFoo.class);
        new Foo().f();
    }
}
