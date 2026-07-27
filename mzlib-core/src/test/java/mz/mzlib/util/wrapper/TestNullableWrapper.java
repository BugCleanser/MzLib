package mz.mzlib.util.wrapper;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestNullableWrapper
{
    static class Foo
    {
        @Nullable Foo requireNonNull(@Nullable Foo value)
        {
            assertNotNull(value);
            return value;
        }
        
        @Nullable Foo requireNull(@Nullable Foo value)
        {
            assertNull(value);
            return null;
        }
    }
    
    @WrapClass(Foo.class)
    interface WrapperFoo extends WrapperObject.Generic<Foo>
    {
        WrapperFactory<WrapperFoo> FACTORY = WrapperFactory.of(WrapperFoo.class);
        
        @WrapMethod("requireNonNull")
        @Nullable @NullableWrapper WrapperFoo requireNonNull(@Nullable @NullableWrapper WrapperFoo foo);
        
        @WrapMethod("requireNull")
        @Nullable @NullableWrapper WrapperFoo requireNull(@NullableWrapper WrapperFoo foo);
    }
    
    @Test
    void test()
    {
        WrapperFoo foo = WrapperFoo.FACTORY.create(new Foo());
        assertNotNull(foo.requireNonNull(foo));
        assertNull(foo.requireNull(null));
    }
}
