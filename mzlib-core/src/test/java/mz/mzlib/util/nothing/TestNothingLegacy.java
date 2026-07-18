package mz.mzlib.util.nothing;

import mz.mzlib.MzLib;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.WrapperString;
import mz.mzlib.util.wrapper.basic.Wrapper_int;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@SuppressWarnings("deprecation")
public class TestNothingLegacy
{
    static String last;
    
    static class Foo
    {
        Foo()
        {
        }
        
        static int func()
        {
            return 114;
        }
    }
    
    @WrapClass(Foo.class)
    public interface WrapperFoo extends WrapperObject.Generic<Foo>
    {
        @WrapMethod("func")
        int static$func();
    }
    
    @WrapSameClass(WrapperFoo.class)
    public interface NothingFoo extends Nothing, WrapperFoo
    {
        @NothingInject(wrapperMethodName = "<init>", wrapperMethodParams = {}, type = NothingInjectType.INSERT_BEFORE, locateMethod = "")
        static void static$of$begin()
        {
            last = "constructor";
        }
        
        @NothingInject(wrapperMethodName = "static$func", wrapperMethodParams = {}, type = NothingInjectType.INSERT_BEFORE, locateMethod = "")
        static Wrapper_int static$func$overwrite()
        {
            return Wrapper_int.FACTORY.create(514);
        }
        
    }
    
    @BeforeAll
    static void setUp()
    {
        MzLib.instance.load();
        MzLib.instance.register(NothingFoo.class);
    }
    @AfterAll
    static void tearDown()
    {
        MzLib.instance.unload();
    }
    
    @Test
    void testConstructor()
    {
        last = null;
        new Foo();
        //noinspection ConstantValue
        Assertions.assertEquals("constructor", last);
    }
    
//    @WrapClass(String.class)
//    public interface WrapperString1 extends WrapperObject.Generic<String>
//    {
//        WrapperFactory<WrapperString1> FACTORY = WrapperFactory.of(WrapperString1.class);
//
//        @WrapMethod("toString")
//        WrapperString toStringLegacy();
//    }
    
    @WrapClass(Object.class)
    public interface NothingObject extends Nothing, WrapperObject
    {
        @NothingInject(wrapperMethodName="toString", wrapperMethodParams={}, type=NothingInjectType.INSERT_BEFORE, locateMethod = "")
        default WrapperString toString$overwrite()
        {
            return WrapperString.FACTORY.create("test");
        }
    }
    
    @Test
    void test$toString()
    {
        Object obj = new Object();
        String str = obj.toString();
        Assertions.assertEquals(str, obj.toString());
        MzLib.instance.register(NothingObject.class);
        Assertions.assertEquals("test", obj.toString());
        MzLib.instance.unregister(NothingObject.class);
        Assertions.assertEquals(str, obj.toString());
    }
}
