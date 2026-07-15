package mz.mzlib.util.adapter;

import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_int;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAdapter
{
    @SuppressWarnings("unused")
    static class Foo
    {
        static int inc(int value)
        {
            return value + 1;
        }
    }
    
    @WrapClass(Foo.class)
    public interface WrapperFoo extends WrapperObject
    {
        WrapperFactory<WrapperFoo> FACTORY = WrapperFactory.of(WrapperFoo.class);
        
        static Wrapper_int inc(Wrapper_int value)
        {
            return FACTORY.getStatic().static$inc(value);
        }
        
        
        @WrapMethod("inc")
        Wrapper_int static$inc(Wrapper_int value);
    }
    
    @Test
    public void test()
    {
        Assertions.assertEquals(WrapperFoo.inc(Wrapper_int.FACTORY.create(114)), Wrapper_int.FACTORY.create(115));
    }
}
