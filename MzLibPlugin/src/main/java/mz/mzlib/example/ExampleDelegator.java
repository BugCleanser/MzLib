package mz.mzlib.example;

import mz.mzlib.util.delegator.*;

@Deprecated
public class ExampleDelegator
{
    @SuppressWarnings("all")
    public static class Test implements Cloneable
    {
        private double var = 114.514;
        private Test thiz = this;

        private Test() {}

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
        DelegatorTest.m1();
        DelegatorTest t = DelegatorTest.newInstance();
        t.m();
        t.setVar(1919.810);
        t.m();
    }

    @DelegatorClass(Test.class)
    public interface DelegatorTest extends Delegator
    {
        @DelegatorCreator
        static DelegatorTest create(Object delegate)
        {
            return Delegator.create(DelegatorTest.class, delegate);
        }

        @DelegatorConstructor
        DelegatorTest staticNewInstance();

        static DelegatorTest newInstance()
        {
            return create(null).staticNewInstance();
        }

        @DelegatorMethod("m")
        void m();

        @DelegatorMethod("m1")
        void staticM1();

        static void m1()
        {
            create(null).staticM1();
        }

        @DelegatorFieldAccessor("var")
        void setVar(double var);
    }
}