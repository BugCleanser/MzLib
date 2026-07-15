package mz.mzlib.util.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestMonoid
{
    @Test
    void testBooleanAnd()
    {
        Monoid<Boolean> m = Monoid.BOOLEAN_AND;
        assertTrue(m.identity());
        assertTrue(m.apply(true, true));
        assertFalse(m.apply(true, false));

        assertEquals(true, m.apply(m.identity(), true));
        assertEquals(false, m.apply(m.identity(), false));
    }

    @Test
    void testBooleanOr()
    {
        Monoid<Boolean> m = Monoid.BOOLEAN_OR;
        assertFalse(m.identity());
        assertTrue(m.apply(true, true));
        assertTrue(m.apply(true, false));
        assertFalse(m.apply(false, false));

        assertEquals(true, m.apply(m.identity(), true));
        assertEquals(false, m.apply(m.identity(), false));
    }

    @Test
    void testIntMultiplication()
    {
        Monoid<Integer> m = Monoid.INT_MULTIPLICATION;
        assertEquals(1, m.identity().intValue());
        assertEquals(6, m.apply(2, 3).intValue());
        assertEquals(5, m.apply(m.identity(), 5).intValue());
        assertEquals(5, m.apply(5, m.identity()).intValue());
    }

    @Test
    void testLongMultiplication()
    {
        Monoid<Long> m = Monoid.LONG_MULTIPLICATION;
        assertEquals(1L, m.identity().longValue());
        assertEquals(6L, m.apply(2L, 3L).longValue());
    }

    @Test
    void testByteMultiplication()
    {
        Monoid<Byte> m = Monoid.BYTE_MULTIPLICATION;
        assertEquals(1, m.identity().byteValue());
        assertEquals(6, m.apply((byte) 2, (byte) 3).byteValue());
    }

    @Test
    void testShortMultiplication()
    {
        Monoid<Short> m = Monoid.SHORT_MULTIPLICATION;
        assertEquals(1, m.identity().shortValue());
        assertEquals(6, m.apply((short) 2, (short) 3).shortValue());
    }

    @Test
    void testCustomMonoid()
    {
        Monoid<String> concat = Monoid.build("", (a, b) -> a + b);
        assertEquals("", concat.identity());
        assertEquals("ab", concat.apply("a", "b"));
        assertEquals("a", concat.apply("a", concat.identity()));
        assertEquals("b", concat.apply(concat.identity(), "b"));
    }

    @Test
    void testIsSemigroup()
    {
        Semigroup<String> sg = Monoid.build("", (a, b) -> a + b);
        assertEquals("ab", sg.apply("a", "b"));
    }
}
