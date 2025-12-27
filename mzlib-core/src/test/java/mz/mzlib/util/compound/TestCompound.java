package mz.mzlib.util.compound;

import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCompound
{
    @Test
    public void test()
    {
        WrapperObject bar = Bar.of().as(WrapperObject.FACTORY);
        Assertions.assertInstanceOf(Foo.class, bar.asCompound().unwrap());
    }

    @Compound
    public interface Foo extends WrapperObject
    {
        WrapperFactory<Foo> FACTORY = WrapperFactory.of(Foo.class);
    }
    @Compound
    public interface Bar extends Foo
    {
        WrapperFactory<Bar> FACTORY = WrapperFactory.of(Bar.class);

        static Bar of()
        {
            return FACTORY.getStatic().static$of();
        }


        @WrapConstructor
        Bar static$of();
    }
}
