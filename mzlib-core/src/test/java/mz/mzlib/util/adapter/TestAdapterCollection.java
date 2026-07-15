package mz.mzlib.util.adapter;

import mz.mzlib.util.wrapper.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Collection;

public class TestAdapterCollection
{
    @SuppressWarnings("unused")
    static class Foo
    {
        int value = 114;
        static void in(Collection<Foo> list)
        {
            Assertions.assertEquals(114, list.iterator().next().value);
        }
        static Collection<Foo> out()
        {
            return Collections.singleton(new Foo());
        }
        
        static void in2(Collection<Collection<Foo>> list)
        {
            Assertions.assertEquals(114, list.iterator().next().iterator().next().value);
        }
        static Collection<Collection<Foo>> out2()
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
        
        static void in(Collection<WrapperFoo> list)
        {
            FACTORY.getStatic().static$in(list);
        }
        static Collection<WrapperFoo> out()
        {
            return FACTORY.getStatic().static$out();
        }
        
        static void in2(Collection<Collection<WrapperFoo>> list)
        {
            FACTORY.getStatic().static$in2(list);
        }
        static Collection<Collection<WrapperFoo>> out2()
        {
            return FACTORY.getStatic().static$out2();
        }
        
        
        @WrapMethod("in")
        void static$in(@AdapterCollection Collection<WrapperFoo> list);
        
        @WrapMethod("out")
        @AdapterCollection Collection<WrapperFoo> static$out();
        
        @WrapMethod("in2")
        void static$in2(@AdapterCollection Collection<@AdapterCollection Collection<WrapperFoo>> list);
        
        @WrapMethod("out2")
        @AdapterCollection Collection<@AdapterCollection Collection<WrapperFoo>> static$out2();
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
