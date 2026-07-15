package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestArrayUtil
{
    @Test
    void testBoxInt()
    {
        Integer[] result = ArrayUtil.box(new int[]{1, 2, 3});
        assertArrayEquals(new Integer[]{1, 2, 3}, result);
    }

    @Test
    void testBoxLong()
    {
        Long[] result = ArrayUtil.box(new long[]{1L, 2L, 3L});
        assertArrayEquals(new Long[]{1L, 2L, 3L}, result);
    }

    @Test
    void testBoxDouble()
    {
        Double[] result = ArrayUtil.box(new double[]{1.0, 2.0, 3.0});
        assertArrayEquals(new Double[]{1.0, 2.0, 3.0}, result);
    }

    @Test
    void testBoxBoolean()
    {
        Boolean[] result = ArrayUtil.box(new boolean[]{true, false, true});
        assertArrayEquals(new Boolean[]{true, false, true}, result);
    }

    @Test
    void testBoxChar()
    {
        Character[] result = ArrayUtil.box(new char[]{'a', 'b', 'c'});
        assertArrayEquals(new Character[]{'a', 'b', 'c'}, result);
    }

    @Test
    void testBoxByte()
    {
        Byte[] result = ArrayUtil.box(new byte[]{1, 2, 3});
        assertArrayEquals(new Byte[]{1, 2, 3}, result);
    }

    @Test
    void testBoxShort()
    {
        Short[] result = ArrayUtil.box(new short[]{1, 2, 3});
        assertArrayEquals(new Short[]{1, 2, 3}, result);
    }

    @Test
    void testBoxFloat()
    {
        Float[] result = ArrayUtil.box(new float[]{1.0f, 2.0f, 3.0f});
        assertArrayEquals(new Float[]{1.0f, 2.0f, 3.0f}, result);
    }

    @Test
    void testBoxObjectDynamic()
    {
        assertArrayEquals(new Integer[]{1, 2}, (Integer[]) ArrayUtil.box((Object) new int[]{1, 2}));
        assertArrayEquals(new Long[]{1L}, (Long[]) ArrayUtil.box((Object) new long[]{1L}));
        assertArrayEquals(new Double[]{1.0}, (Double[]) ArrayUtil.box((Object) new double[]{1.0}));
    }

    @Test
    void testBoxObjectInvalid()
    {
        assertThrows(ClassCastException.class, () -> ArrayUtil.box((Object) new Object()));
    }

    @Test
    void testUnboxInt()
    {
        int[] result = ArrayUtil.unbox(new Integer[]{1, 2, 3});
        assertArrayEquals(new int[]{1, 2, 3}, result);
    }

    @Test
    void testUnboxLong()
    {
        long[] result = ArrayUtil.unbox(new Long[]{1L, 2L, 3L});
        assertArrayEquals(new long[]{1L, 2L, 3L}, result);
    }

    @Test
    void testUnboxDouble()
    {
        double[] expected = {1.0, 2.0, 3.0};
        double[] result = ArrayUtil.unbox(new Double[]{1.0, 2.0, 3.0});
        assertArrayEquals(expected, result, 0.0);
    }

    @Test
    void testUnboxBoolean()
    {
        boolean[] result = ArrayUtil.unbox(new Boolean[]{true, false});
        assertArrayEquals(new boolean[]{true, false}, result);
    }

    @Test
    void testUnboxChar()
    {
        char[] result = ArrayUtil.unbox(new Character[]{'a', 'b'});
        assertArrayEquals(new char[]{'a', 'b'}, result);
    }

    @Test
    void testUnboxByte()
    {
        byte[] result = ArrayUtil.unbox(new Byte[]{1, 2});
        assertArrayEquals(new byte[]{1, 2}, result);
    }

    @Test
    void testUnboxShort()
    {
        short[] result = ArrayUtil.unbox(new Short[]{1, 2});
        assertArrayEquals(new short[]{1, 2}, result);
    }

    @Test
    void testUnboxFloat()
    {
        float[] expected = {1.0f, 2.0f};
        float[] result = ArrayUtil.unbox(new Float[]{1.0f, 2.0f});
        assertArrayEquals(expected, result, 0.0f);
    }

    @Test
    void testUnboxDynamic()
    {
        int[] ints = (int[]) ArrayUtil.unbox(new Integer[]{1, 2});
        assertArrayEquals(new int[]{1, 2}, ints);
    }

    @Test
    void testUnboxInvalid()
    {
        assertThrows(ClassCastException.class, () -> ArrayUtil.unbox(new Object[]{"not primitive"}));
    }

    @Test
    void testRoundTrip()
    {
        int[] original = {5, 10, 15};
        Integer[] boxed = ArrayUtil.box(original);
        int[] unboxed = ArrayUtil.unbox(boxed);
        assertArrayEquals(original, unboxed);
    }
}
