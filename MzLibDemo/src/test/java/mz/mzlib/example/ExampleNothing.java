package mz.mzlib.example;

import mz.mzlib.MzLib;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.WrapperString;
import org.junit.jupiter.api.Test;

@Deprecated
public class ExampleNothing
{
    @WrapClass(Object.class)
    public interface NothingObject extends WrapperObject, Nothing
    {
        @WrapMethod("toString")
        String toString();

        @NothingInject(wrapperMethod="toString", locateMethod="", type = NothingInjectType.INSERT_BEFORE)
        default WrapperString injectionToString()
        {
            return WrapperString.create("Hello World");
        }
    }

    @Test
    public void test()
    {
        MzLib.instance.load();
        MzLib.instance.register(NothingObject.class);
        System.out.println(new Object());
    }
}
