package mz.mzlib.util.adapter;

import mz.mzlib.util.wrapper.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestBridgeAdapter
{
    static class RawFoo
    {
        int value = 114;
    }
    @SuppressWarnings("unused")
    static class RawBar
    {
        void in(RawFoo value)
        {
            Assertions.assertEquals(114, value.value);
        }
        RawFoo out()
        {
            return new RawFoo();
        }
    }
    
    @Adapter(BridgeAdapter.Processor.class)
    public interface Foo extends BridgeAdapter<FooImpl>
    {
        static Foo of()
        {
            return  FooImpl.of();
        }
        
        int getValue();
    }
    @WrapClass(RawFoo.class)
    public interface FooImpl extends Foo, WrapperObject.Generic<RawFoo>
    {
        WrapperFactory<FooImpl> FACTORY = WrapperFactory.of(FooImpl.class);
        
        static FooImpl of()
        {
            return FACTORY.getStatic().static$of();
        }
        
        @Override
        @WrapFieldAccessor("value")
        int getValue();
        
        
        @WrapConstructor
        FooImpl static$of();
    }
    
    @WrapClass(RawBar.class)
    public interface Bar extends WrapperObject.Generic<RawBar>
    {
        WrapperFactory<Bar> FACTORY = WrapperFactory.of(Bar.class);
        
        static Bar of()
        {
            return FACTORY.getStatic().static$of();
        }
        
        @WrapMethod("in")
        void in(Foo value);
        
        @WrapMethod("out")
        Foo out();
        
        
        @WrapConstructor
        Bar static$of();
    }
    
    @Test
    public void test()
    {
        Bar bar = Bar.of();
        bar.in(Foo.of());
        Assertions.assertEquals(114, bar.out().getValue());
    }
}
