package mz.mzlib.util.adapter;

import mz.mzlib.util.wrapper.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestAdapterList
{
    @SuppressWarnings("unused")
    static class Foo
    {
        int value = 114;
        static void in(List<Foo> list)
        {
            Assertions.assertEquals(114, list.get(0).value);
        }
        static List<Foo> out()
        {
            return Arrays.asList(new Foo(), new Foo());
        }
        
        static void in2(List<List<Foo>> list)
        {
            Assertions.assertEquals(114, list.get(0).get(0).value);
        }
        static List<List<Foo>> out2()
        {
            return Collections.singletonList(Collections.singletonList(new Foo()));
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
        
        
        @WrapMethod("in")
        void static$in(@AdapterList List<WrapperFoo> list);
        
        @WrapMethod("out")
        @AdapterList List<WrapperFoo> static$out();
        
        @WrapMethod("in2")
        void static$in2(@AdapterList List<@AdapterList List<WrapperFoo>> list);
        
        @WrapMethod("out2")
        @AdapterList List<@AdapterList List<WrapperFoo>> static$out2();
    }
    
    @Test
    public void test()
    {
        WrapperFoo.in(Collections.singletonList(WrapperFoo.FACTORY.create(new Foo())));
        Assertions.assertEquals(114, WrapperFoo.out().get(0).getValue());
        
        WrapperFoo.in2(Collections.singletonList(Collections.singletonList(WrapperFoo.FACTORY.create(new Foo()))));
        Assertions.assertEquals(114, WrapperFoo.out2().get(0).get(0).getValue());
    }
}
