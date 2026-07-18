package mz.mzlib.util.nothing;

import mz.mzlib.MzLib;
import mz.mzlib.util.Box;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.adapter.AdapterPrimitive;
import mz.mzlib.util.wrapper.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

public class TestNothing
{
    static List<String> order = new ArrayList<>();
    
    public static class Bar
    {
        Bar()
        {
        }
        Bar(Object a, Bar bar, Object b)
        {
            order.add("super");
        }
    }
    
    @SuppressWarnings("all")
    static class Foo extends Bar
    {
        static int func()
        {
            return 114;
        }
        
        Foo()
        {
            super(order.add("pre"), new Bar(), order.add("post"));
            order.add("init");
        }
        
        int func1()
        {
            order.add("func1");
            return 114;
        }
        
        int func2(int a)
        {
            return a;
        }
        
        Throwable func3()
        {
            if(RuntimeUtil.cast(true))
                throw new Error();
            return new RuntimeException();
        }
    }
    
    @WrapClass(Foo.class)
    interface WrapperFoo extends WrapperObject.Generic<Foo>
    {
        @WrapMethod("func1")
        int func1();
        
        @WrapMethod("func2")
        int func2(int arg);
        
        @WrapMethod("func3")
        Throwable func3();
        
        
        @WrapMethod("func")
        int static$func();
        
        @WrapConstructor
        WrapperFoo static$of();
    }
    
    @BeforeAll
    static void setUp()
    {
        MzLib.instance.load();
    }
    @AfterAll
    static void tearDown()
    {
        MzLib.instance.unload();
    }
    
    @WrapSameClass(WrapperFoo.class)
    interface testStatic$Nothing extends Nothing, WrapperFoo
    {
        @NothingInject(name = "static$func", params = {}, type = NothingInjectType.INSERT_BEFORE, locator = "stay")
        static Box<@AdapterPrimitive Integer> static$func$overwrite()
        {
            return Box.of(514);
        }
    }
    @Test
    void testStatic()
    {
        MzLib.instance.register(testStatic$Nothing.class);
        Assertions.assertEquals(514, Foo.func());
        MzLib.instance.unregister(testStatic$Nothing.class);
    }
    
    @WrapSameClass(WrapperFoo.class)
    interface testConstructor$Nothing extends Nothing, WrapperFoo
    {
        @NothingInject(name = "static$of", params = {}, type = NothingInjectType.INSERT_BEFORE, locator = "afterSuper")
        default void static$of$begin()
        {
            order.add("inject");
        }
    }
    @Test
    void testConstructor()
    {
        MzLib.instance.register(testConstructor$Nothing.class);
        order.clear();
        new Foo();
        assertEquals(Arrays.asList("pre", "post", "super", "inject", "init"), order);
        MzLib.instance.unregister(testConstructor$Nothing.class);
    }
    
    @WrapSameClass(WrapperFoo.class)
    interface testNoThis$Nothing extends Nothing, WrapperFoo
    {
        @NothingInject(name = "func1", type = NothingInjectType.INSERT_BEFORE, locator = "stay")
        static void func1$begin()
        {
            order.add("inject");
        }
    }
    @Test
    void testNoThis()
    {
        MzLib.instance.register(testNoThis$Nothing.class);
        Foo foo = new Foo();
        order.clear();
        foo.func1();
        assertEquals(Arrays.asList("inject", "func1"), order);
        MzLib.instance.unregister(testNoThis$Nothing.class);
    }
    
    @WrapSameClass(WrapperFoo.class)
    interface testArgument$Nothing extends Nothing, WrapperFoo
    {
        @NothingInject(name = "func2", type = NothingInjectType.INSERT_BEFORE, locator = "stay")
        default void func2$begin(@LocalVar(1) Box.Mut<@AdapterPrimitive Integer> arg)
        {
            assertEquals(114, arg.get());
            arg.set(514);
        }
    }
    @Test
    void testArgument()
    {
        MzLib.instance.register(testArgument$Nothing.class);
        assertEquals(514, new Foo().func2(114));
        MzLib.instance.unregister(testArgument$Nothing.class);
    }
    
    @WrapSameClass(WrapperFoo.class)
    interface testStackTop$Nothing extends Nothing, WrapperFoo
    {
        @NothingInject(name = "func2", type = NothingInjectType.INSERT_BEFORE, locator = "followingReturn")
        default void func2$begin(@StackTop Box.Mut<@AdapterPrimitive Integer> returnValue)
        {
            assertEquals(114, returnValue.get());
            returnValue.set(514);
        }
    }
    @Test
    void testStackTop()
    {
        MzLib.instance.register(testStackTop$Nothing.class);
        assertEquals(514, new Foo().func2(114));
        MzLib.instance.unregister(testStackTop$Nothing.class);
    }
    
    @WrapSameClass(WrapperFoo.class)
    interface testBrTrue$Nothing extends Nothing, WrapperFoo
    {
        @NothingInject(name = "func3", type = NothingInjectType.BRTRUE, locator = { "followingThrow", "followingReturn" })
        default boolean func3$inject(@StackTop Box.Mut<Throwable> exception)
        {
            assertInstanceOf(Error.class, exception.get());
            return true;
        }
    }
    @Test
    void testBrTrue()
    {
        assertThrows(Error.class, new Foo()::func3);
        MzLib.instance.register(testBrTrue$Nothing.class);
        assertInstanceOf(Error.class, new Foo().func3());
        MzLib.instance.unregister(testBrTrue$Nothing.class);
    }
    
    @WrapSameClass(WrapperFoo.class)
    interface testGoto$Nothing extends Nothing, WrapperFoo
    {
        @NothingInject(name = "func3", type = NothingInjectType.BRTRUE, locator = { "followingThrow", "followingReturn" })
        default void func3$inject(@StackTop Box.Mut<Throwable> exception)
        {
            assertInstanceOf(Error.class, exception.get());
        }
    }
    @Test
    void testGoto()
    {
        assertThrows(Error.class, new Foo()::func3);
        MzLib.instance.register(testGoto$Nothing.class);
        assertInstanceOf(Error.class, new Foo().func3());
        MzLib.instance.unregister(testGoto$Nothing.class);
    }
    
    @WrapSameClass(WrapperFoo.class)
    interface testCatch$Nothing extends Nothing, WrapperFoo
    {
        default void afterThrow(NothingInjectLocating locating)
        {
            locating.followingThrow();
            locating.offset(1);
        }
        @NothingInject(name = "func3", type = NothingInjectType.CATCH, locator = { "followingThrow", "afterThrow" })
        default void func3$inject(@StackTop Box.Mut<Throwable> exception)
        {
            assertInstanceOf(Error.class, exception.get());
        }
    }
    @Test
    void testCatch()
    {
        assertThrows(Error.class, new Foo()::func3);
        MzLib.instance.register(testCatch$Nothing.class);
        assertInstanceOf(RuntimeException.class, new Foo().func3());
        MzLib.instance.unregister(testCatch$Nothing.class);
    }
    
    @WrapSameClass(WrapperFoo.class)
    interface testCatchReturn$Nothing extends Nothing, WrapperFoo
    {
        default void afterThrow(NothingInjectLocating locating)
        {
            locating.followingThrow();
            locating.offset(1);
        }
        @NothingInject(name = "func3", type = NothingInjectType.CATCH, locator = { "followingThrow", "afterThrow" })
        default Box<Throwable> func3$inject(@StackTop Box.Mut<Throwable> exception)
        {
            assertInstanceOf(Error.class, exception.get());
            return Box.of(new Error());
        }
    }
    @Test
    void testCatchReturn()
    {
        assertThrows(Error.class, new Foo()::func3);
        MzLib.instance.register(testCatchReturn$Nothing.class);
        assertInstanceOf(Error.class, new Foo().func3());
        MzLib.instance.unregister(testCatchReturn$Nothing.class);
    }
    
    @Test
    void testPriority()
    {
        NothingTargetData.Element e1 = new NothingTargetData.Element(), e2 = new NothingTargetData.Element();
        e1.priority = 1;
        e2.priority = 2;
        PriorityQueue<NothingTargetData.Element> queue = new PriorityQueue<>();
        queue.add(e1);
        queue.add(e2);
        assertEquals(e2, queue.poll());
    }
}
