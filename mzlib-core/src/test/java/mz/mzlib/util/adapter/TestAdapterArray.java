package mz.mzlib.util.adapter;

import mz.mzlib.util.wrapper.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAdapterArray
{
    @SuppressWarnings("unused")
    static class Foo
    {
        int value = 114;
        
        static void in(Foo[] list)
        {
            assertEquals(114, list[0].value);
        }
        static Foo[] out()
        {
            return new Foo[]{ new Foo(), new Foo() };
        }
        
        static void in2(Foo[][] list)
        {
            assertEquals(114, list[0][0].value);
        }
        static Foo[][] out2()
        {
            return new Foo[][]{ new Foo[]{ new Foo() } };
        }
        
        static int[] primitive(long[] arr)
        {
            assertEquals(114L, arr[0]);
            return new int[]{ 114, 514 };
        }
    }
    
    @WrapClass(Foo.class)
    public interface WrapperFoo extends WrapperObject.Generic<Foo>
    {
        WrapperFactory<WrapperFoo> FACTORY = WrapperFactory.of(WrapperFoo.class);
        
        @WrapFieldAccessor("value")
        int getValue();
        
        static void in(List<WrapperFoo> list)
        {
            FACTORY.getStatic().static$in(list);
        }
        static List<WrapperFoo> out()
        {
            return FACTORY.getStatic().static$out();
        }
        
        static void in2(List<List<WrapperFoo>> list)
        {
            FACTORY.getStatic().static$in2(list);
        }
        static List<List<WrapperFoo>> out2()
        {
            return FACTORY.getStatic().static$out2();
        }
        
        static List<Integer> primitive(List<Long> array)
        {
            return FACTORY.getStatic().static$primitive(array);
        }
        
        
        @WrapMethod("in")
        void static$in(@AdapterArray List<WrapperFoo> list);
        
        @WrapMethod("out")
        @AdapterArray List<WrapperFoo> static$out();
        
        @WrapMethod("in2")
        void static$in2(@AdapterArray List<@AdapterArray List<WrapperFoo>> list);
        
        @WrapMethod("out2")
        @AdapterArray List<@AdapterArray List<WrapperFoo>> static$out2();
        
        @WrapMethod("primitive")
        @AdapterArray List<@AdapterPrimitive Integer> static$primitive(@AdapterArray List<@AdapterPrimitive Long> array);
    }
    
    @Test
    public void test()
    {
        WrapperFoo.in(Collections.singletonList(WrapperFoo.FACTORY.create(new Foo())));
        assertEquals(114, WrapperFoo.out().get(0).getValue());
    }
    
    @Test
    public void test2()
    {
        WrapperFoo.in2(Collections.singletonList(Collections.singletonList(WrapperFoo.FACTORY.create(new Foo()))));
        assertEquals(114, WrapperFoo.out2().get(0).get(0).getValue());
    }
    
    @Test
    public void testPrimitive()
    {
        List<Integer> r = WrapperFoo.primitive(Arrays.asList(114L, 514L));
        assertEquals(114, r.get(0));
    }
}
