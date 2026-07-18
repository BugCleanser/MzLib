package mz.mzlib.example;

import mz.mzlib.util.wrapper.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestWrapper
{
    public static class Foo
    {
        private final double var = 114.514;
        private final Foo thiz = this;

        private Foo()
        {
        }

        private void m()
        {
            System.out.println(thiz.var);
        }

        private static void m1()
        {
            System.out.println("HelloWorld");
        }
    }

    @WrapClass(Foo.class)
    public interface WrapperFoo extends WrapperObject
    {
        WrapperFactory<WrapperFoo> FACTORY = WrapperFactory.of(WrapperFoo.class);

        @WrapMethod("m")
        void m();

        @WrapMethod("m1")
        void static$m1();
        static void m1()
        {
            FACTORY.getStatic().static$m1();
        }

        @WrapConstructor
        WrapperFoo static$newInstance();
        static WrapperFoo newInstance()
        {
            return FACTORY.getStatic().static$newInstance();
        }

        @WrapFieldAccessor("var")
        double getVar();
        @WrapFieldAccessor("var")
        void setVar(double var);
    }

    @Test
    public void test()
    {
        WrapperFoo.m1();
        WrapperFoo test = WrapperFoo.newInstance();
        test.m();
        System.out.println("Debug: " + test.getVar());
        test.setVar(1919810);
        test.m();
    }
    
    @Test
    public void test$getWrappedClass()
    {
        assertEquals(Foo.class, WrapperFoo.FACTORY.getWrappedClass());
    }

    @WrapSameClass(WrapperObject.class)
    public interface A extends WrapperObject
    {
        @CallOnce
        default void f(List<Class<?>> l)
        {
            l.add(A.class);
        }
    }
    @WrapSameClass(A.class)
    public interface B extends A
    {
        @Override
        default void f(List<Class<?>> l)
        {
            l.add(B.class);
        }
    }
    @WrapSameClass(A.class)
    public interface C extends A
    {
        @Override
        default void f(List<Class<?>> l)
        {
            l.add(C.class);
        }
    }
    @WrapSameClass(A.class)
    public interface D extends B, C
    {
        @Override
        default void f(List<Class<?>> l)
        {
            l.add(D.class);
        }
    }
    @Test
    public void testCallOnce()
    {
        List<Class<?>> l = new ArrayList<>();
        WrapperFactory.of(D.class).getStatic().f(l);
        assert l.size() == 4;
        assert l.contains(A.class);
        assert l.contains(B.class);
        assert l.contains(C.class);
        assert l.contains(D.class);
    }
}