package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class TestLazyConstant
{
    @Test
    void testLazyInit()
    {
        AtomicInteger counter = new AtomicInteger(0);
        LazyConstant<String> c = LazyConstant.of(() ->
        {
            counter.incrementAndGet();
            return "initialized";
        });

        assertEquals(0, counter.get());
        assertEquals("initialized", c.get());
        assertEquals(1, counter.get());
    }

    @Test
    void testOnlyInitsOnce()
    {
        AtomicInteger counter = new AtomicInteger(0);
        LazyConstant<String> c = LazyConstant.of(() ->
        {
            counter.incrementAndGet();
            return "initialized";
        });

        c.get();
        c.get();
        c.get();
        assertEquals(1, counter.get());
    }

    @Test
    void testNullValue()
    {
        AtomicInteger counter = new AtomicInteger(0);
        LazyConstant<String> c = LazyConstant.of(() ->
        {
            counter.incrementAndGet();
            return null;
        });

        assertNull(c.get());
        assertEquals(1, counter.get());
        assertNull(c.get());
        assertEquals(1, counter.get());
    }

    @Test
    void testImplementsSupplier()
    {
        LazyConstant<Integer> c = LazyConstant.of(() -> 42);
        assertEquals(42, ((java.util.function.Supplier<Integer>) c).get());
    }

    @Test
    void testManualInit()
    {
        AtomicInteger counter = new AtomicInteger(0);
        LazyConstant<String> c = new LazyConstant<>(() ->
        {
            counter.incrementAndGet();
            return "manual";
        });

        c.init();
        assertEquals(1, counter.get());
        c.get();
        assertEquals(1, counter.get());
    }
}
