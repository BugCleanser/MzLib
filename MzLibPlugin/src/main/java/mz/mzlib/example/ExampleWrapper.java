package mz.mzlib.example;

import mz.mzlib.util.wrapper.*;

@Deprecated
public class ExampleWrapper
{
    @SuppressWarnings("all")
    public static class Test implements Cloneable
    {
        private double var = 114.514;
        private Test thiz = this;

        private Test()
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

    public static void main(String[] args)
    {
        WrapperTest.m1();
        WrapperTest test=WrapperTest.newInstance();
        test.m();
        System.out.println("Debug: "+test.getVar());
        test.setVar(1919810);
        test.m();
    }

    @WrapClass(Test.class)
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
}