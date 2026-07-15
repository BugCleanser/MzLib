package mz.mzlib.util.adapter;

import mz.mzlib.util.wrapper.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

public class TestAdapterSet
{
    @SuppressWarnings("unused")
    static class Foo
    {
        int value = 114;
        static void in(Set<Foo> list)
        {
            Assertions.assertEquals(114, list.iterator().next().value);
        }
        static Set<Foo> out()
        {
            return Collections.singleton(new Foo());
        }
        
        static void in2(Set<Set<Foo>> list)
        {
            Assertions.assertEquals(114, list.iterator().next().iterator().next().value);
        }
        static Set<Set<Foo>> out2()
        {
            return Collections.singleton(Collections.singleton(new Foo()));
        }
    }
    
    @WrapClass(Foo.class)
    public interface WrapperFoo extends WrapperObject.Generic<Foo>
    {
        WrapperFactory<WrapperFoo> FACTORY = WrapperFactory.of(WrapperFoo.class);
        
        @WrapFieldAccessor("value")
        int getValue();
        
        static void in(Set<WrapperFoo> list)
        {
            FACTORY.getStatic().static$in(list);
        }
        static Set<WrapperFoo> out()
        {
            return FACTORY.getStatic().static$out();
        }
        
        static void in2(Set<Set<WrapperFoo>> list)
        {
            FACTORY.getStatic().static$in2(list);
        }
        static Set<Set<WrapperFoo>> out2()
        {
            return FACTORY.getStatic().static$out2();
        }
        
        
        @WrapMethod("in")
        void static$in(@AdapterSet Set<WrapperFoo> list);
        
        @WrapMethod("out")
        @AdapterSet Set<WrapperFoo> static$out();
        
        @WrapMethod("in2")
        void static$in2(@AdapterSet Set<@AdapterSet Set<WrapperFoo>> list);
        
        @WrapMethod("out2")
        @AdapterSet Set<@AdapterSet Set<WrapperFoo>> static$out2();
    }
    
    @Test
    public void test()
    {
        WrapperFoo.in(Collections.singleton(WrapperFoo.FACTORY.create(new Foo())));
        Assertions.assertEquals(114, WrapperFoo.out().iterator().next().getValue());
        
        WrapperFoo.in2(Collections.singleton(Collections.singleton(WrapperFoo.FACTORY.create(new Foo()))));
        Assertions.assertEquals(114, WrapperFoo.out2().iterator().next().iterator().next().getValue());
    }
}
