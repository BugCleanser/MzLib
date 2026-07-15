package mz.mzlib.util.adapter;

import mz.mzlib.util.wrapper.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

public class TestAdapterMap
{
    @SuppressWarnings("unused")
    static class Foo
    {
        static Foo INSTANCE = new Foo();
        int value = 114;
        
        void inKey(Map<Foo, Integer> map)
        {
            Assertions.assertNull(map.get(new Foo()));
            Assertions.assertEquals(514, map.get(INSTANCE));
        }
        Map<Foo, Integer> outKey()
        {
            return Collections.singletonMap(INSTANCE, 514);
        }
        
        void inValue(Map<Integer, Foo> map)
        {
            Assertions.assertNull(map.get(114));
            Assertions.assertEquals(INSTANCE, map.get(514));
        }
        Map<Integer, Foo> outValue()
        {
            return Collections.singletonMap(514, INSTANCE);
        }
    }
    
    @WrapClass(Foo.class)
    public interface WrapperFoo extends WrapperObject.Generic<Foo>
    {
        WrapperFactory<WrapperFoo> FACTORY = WrapperFactory.of(WrapperFoo.class);
        
        WrapperFoo INSTANCE = FACTORY.getStatic().static$INSTANCE();
        
        @WrapFieldAccessor("value")
        int getValue();
        
        @WrapMethod("inKey")
        void inKey(@AdapterMap Map<WrapperFoo, Integer> map);
        @WrapMethod("outKey")
        @AdapterMap Map<WrapperFoo, Integer> outKey();
        
        @WrapMethod("inValue")
        void inValue(@AdapterMap Map<Integer, WrapperFoo> map);
        @WrapMethod("outValue")
        @AdapterMap Map<Integer, WrapperFoo> outValue();
        
        
        @WrapFieldAccessor("INSTANCE")
        WrapperFoo static$INSTANCE();
    }
    
    @Test
    public void test()
    {
        Assertions.assertEquals(114, WrapperFoo.INSTANCE.getValue());
        
        WrapperFoo.INSTANCE.inKey(Collections.singletonMap(WrapperFoo.INSTANCE, 514));
        Map<WrapperFoo, Integer> outKey = WrapperFoo.INSTANCE.outKey();
        //noinspection SuspiciousMethodCalls
        Assertions.assertNull(outKey.get(123));
        Assertions.assertEquals(514, outKey.get(WrapperFoo.INSTANCE));
        
        WrapperFoo.INSTANCE.inValue(Collections.singletonMap(514, WrapperFoo.INSTANCE));
        Map<Integer, WrapperFoo> outValue = WrapperFoo.INSTANCE.outValue();
        Assertions.assertNull(outValue.get(123));
        Assertions.assertEquals(WrapperFoo.INSTANCE, outValue.get(514));
    }
}
