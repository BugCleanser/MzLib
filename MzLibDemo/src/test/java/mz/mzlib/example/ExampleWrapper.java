package mz.mzlib.example;

import mz.mzlib.util.math.Ring;
import mz.mzlib.util.wrapper.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ExampleWrapper
{
    @SuppressWarnings("all")
    public static class TestClass implements Cloneable
    {
        private double var = 114.514;
        private TestClass thiz = this;

        private TestClass()
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
    
    @WrapClass(TestClass.class)
    public interface WrapperTest extends WrapperObject
    {
        @WrapperCreator
        static WrapperTest create(Object wrapped)
        {
            return WrapperObject.create(WrapperTest.class, wrapped);
        }
        
        @WrapMethod("m")
        void m();
        
        @WrapMethod("m1")
        void staticM1();
        static void m1()
        {
            create(null).staticM1();
        }
        
        @WrapConstructor
        WrapperTest staticNewInstance();
        static WrapperTest newInstance()
        {
            return create(null).staticNewInstance();
        }
        
        @WrapFieldAccessor("var")
        double getVar();
        @WrapFieldAccessor("var")
        void setVar(double var);
    }

    @Test
    public void test()
    {
        WrapperTest.m1();
        WrapperTest test=WrapperTest.newInstance();
        test.m();
        System.out.println("Debug: "+test.getVar());
        test.setVar(1919810);
        test.m();
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
    public void test2()
    {
        List<Class<?>> l=new ArrayList<>();
        WrapperObject.create(D.class, null).f(l);
        assert l.size()==4;
        assert l.contains(A.class);
        assert l.contains(B.class);
        assert l.contains(C.class);
        assert l.contains(D.class);
    }
}