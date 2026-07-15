package mz.mzlib.util.wrapper;

import mz.mzlib.util.SimpleCloneable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestWrapperObject
{
    @Test
    public void test()
    {
        //noinspection ConstantValue
        System.out.println(Foo.FACTORY.create(this).equals((Object)null));
    }

    @WrapClass(TestWrapperObject.class)
    public interface Foo extends WrapperObject
    {
        WrapperFactory<Foo> FACTORY = WrapperFactory.of(Foo.class);

        @Override
        boolean equals(Object object);

        @SpecificImpl("equals")
        default boolean equals$impl$test(Object object)
        {
            return true;
        }
    }

    @Test
    public void testClone()
    {
        ACloneable a = new ACloneable();
        Object b = WrapperACloneable.FACTORY.create(a).clone().getWrapped();
        Assertions.assertInstanceOf(ACloneable.class, b);
        Assertions.assertNotSame(a, b);
    }
    public static class ACloneable extends SimpleCloneable<ACloneable>
    {
    }
    @WrapClass(ACloneable.class)
    public interface WrapperACloneable extends WrapperObject
    {
        WrapperFactory<WrapperACloneable> FACTORY = WrapperFactory.of(WrapperACloneable.class);
    }
    
    public static class TestGeneric
    {
        static class Foo
        {
            int value = 114514;
        }
        @WrapClass(Foo.class)
        public interface WrapperFoo extends WrapperObject.Generic<Foo>
        {
            WrapperFactory<WrapperFoo> FACTORY = WrapperFactory.of(WrapperFoo.class);
        }
        
        @Test
        public void test()
        {
            Assertions.assertEquals(114514, WrapperFoo.FACTORY.create(new Foo()).getWrapped().value);
        }
    }
}
