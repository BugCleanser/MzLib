package mz.mzlib.example;

import mz.mzlib.MzLib;
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
        MzLib.instance.register(NothingFoo.class);
        new Foo().f();
    }
}
