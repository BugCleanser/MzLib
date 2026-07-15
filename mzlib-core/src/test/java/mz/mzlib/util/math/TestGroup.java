package mz.mzlib.util.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestGroup
{
    @Test
    void testBooleanXor()
    {
        Group<Boolean> g = Group.BOOLEAN_XOR;
        assertFalse(g.identity());
        assertTrue(g.apply(true, false));
        assertFalse(g.apply(true, true));
        // XOR: each element is its own inverse (x ^ x = 0 = identity)
        assertEquals(true, g.inverse(true));
        assertEquals(false, g.inverse(false));
        assertEquals(false, g.apply(true, g.inverse(true)));
    }

    @Test
    void testIntAddition()
    {
        Group<Integer> g = Group.INT_ADDITION;
        assertEquals(0, g.identity().intValue());
        assertEquals(5, g.apply(2, 3).intValue());
        assertEquals(-5, g.inverse(5).intValue());
        assertEquals(0, g.apply(5, g.inverse(5)).intValue());

        assertEquals(5, g.apply(g.identity(), 5).intValue());
    }

    @Test
    void testLongAddition()
    {
        Group<Long> g = Group.LONG_ADDITION;
        assertEquals(0L, g.identity().longValue());
        assertEquals(5L, g.apply(2L, 3L).longValue());
        assertEquals(-5L, g.inverse(5L).longValue());
    }

    @Test
    void testByteAddition()
    {
        Group<Byte> g = Group.BYTE_ADDITION;
        assertEquals(0, g.identity().byteValue());
        assertEquals(5, g.apply((byte) 2, (byte) 3).byteValue());
        assertEquals(-5, g.inverse((byte) 5).byteValue());
    }

    @Test
    void testShortAddition()
    {
        Group<Short> g = Group.SHORT_ADDITION;
        assertEquals(0, g.identity().shortValue());
        assertEquals(5, g.apply((short) 2, (short) 3).shortValue());
    }

    @Test
    void testFloatAddition()
    {
        Group<Float> g = Group.FLOAT_ADDITION;
        assertEquals(0.0f, g.identity(), 0.0f);
        assertEquals(5.0f, g.apply(2.0f, 3.0f), 0.0f);
        assertEquals(-5.0f, g.inverse(5.0f), 0.0f);
        assertEquals(0.0f, g.apply(5.0f, g.inverse(5.0f)), 0.0001f);
    }

    @Test
    void testFloatMultiplication()
    {
        Group<Float> g = Group.FLOAT_MULTIPLICATION;
        assertEquals(1.0f, g.identity(), 0.0f);
        assertEquals(6.0f, g.apply(2.0f, 3.0f), 0.0f);
        assertEquals(0.5f, g.inverse(2.0f), 0.0f);
        assertEquals(1.0f, g.apply(2.0f, g.inverse(2.0f)), 0.0001f);
    }

    @Test
    void testDoubleAddition()
    {
        Group<Double> g = Group.DOUBLE_ADDITION;
        assertEquals(0.0, g.identity(), 0.0);
        assertEquals(5.0, g.apply(2.0, 3.0), 0.0);
        assertEquals(-5.0, g.inverse(5.0), 0.0);
    }

    @Test
    void testDoubleMultiplication()
    {
        Group<Double> g = Group.DOUBLE_MULTIPLICATION;
        assertEquals(1.0, g.identity(), 0.0);
        assertEquals(6.0, g.apply(2.0, 3.0), 0.0);
        assertEquals(0.5, g.inverse(2.0), 0.0);
        assertEquals(1.0, g.apply(2.0, g.inverse(2.0)), 0.0001);
    }

    @Test
    void testIsMonoid()
    {
        Monoid<Integer> m = Group.INT_ADDITION;
        assertEquals(0, m.identity().intValue());
        assertEquals(5, m.apply(2, 3).intValue());
    }
}
