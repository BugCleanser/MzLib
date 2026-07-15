package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestEither
{
    @Test
    void testFirst()
    {
        Either<String, Integer> e = Either.first("hello");
        assertTrue(e.isFirst());
        assertFalse(e.isSecond());
        assertEquals("hello", e.getFirst().unwrap());
        assertTrue(e.getSecond().isNone());
    }

    @Test
    void testSecond()
    {
        Either<String, Integer> e = Either.second(42);
        assertFalse(e.isFirst());
        assertTrue(e.isSecond());
        assertTrue(e.getFirst().isNone());
        assertEquals(42, e.getSecond().unwrap());
    }

    @Test
    void testInverse()
    {
        Either<String, Integer> e1 = Either.first("hello");
        Either<Integer, String> inv1 = e1.inverse();
        assertTrue(inv1.isSecond());
        assertEquals("hello", inv1.getSecond().unwrap());

        Either<String, Integer> e2 = Either.second(42);
        Either<Integer, String> inv2 = e2.inverse();
        assertTrue(inv2.isFirst());
        assertEquals(42, inv2.getFirst().unwrap());
    }

    @Test
    void testMapFirst()
    {
        Either<Integer, String> e1 = Either.first(5);
        Either<String, String> mapped1 = e1.mapFirst(i -> "number:" + i);
        assertTrue(mapped1.isFirst());
        assertEquals("number:5", mapped1.getFirst().unwrap());

        Either<Integer, String> e2 = Either.second("hello");
        Either<String, String> mapped2 = e2.mapFirst(i -> "number:" + i);
        assertTrue(mapped2.isSecond());
        assertEquals("hello", mapped2.getSecond().unwrap());
    }

    @Test
    void testMapSecond()
    {
        Either<String, Integer> e1 = Either.first("hello");
        Either<String, String> mapped1 = e1.mapSecond(i -> "value:" + i);
        assertTrue(mapped1.isFirst());
        assertEquals("hello", mapped1.getFirst().unwrap());

        Either<String, Integer> e2 = Either.second(42);
        Either<String, String> mapped2 = e2.mapSecond(i -> "value:" + i);
        assertTrue(mapped2.isSecond());
        assertEquals("value:42", mapped2.getSecond().unwrap());
    }

    @Test
    void testFold()
    {
        Either<String, Integer> e1 = Either.first("hello");
        int result1 = e1.fold(String::length, i -> i * 2);
        assertEquals(5, result1);

        Either<String, Integer> e2 = Either.second(42);
        int result2 = e2.fold(String::length, i -> i * 2);
        assertEquals(84, result2);
    }

    @Test
    void testFromNullable()
    {
        Either<String, Integer> e1 = Either.fromNullable("hello", null);
        assertTrue(e1.isFirst());
        assertEquals("hello", e1.getFirst().unwrap());

        Either<String, Integer> e2 = Either.fromNullable(null, 42);
        assertTrue(e2.isSecond());
        assertEquals(42, e2.getSecond().unwrap());

        assertThrows(IllegalArgumentException.class, () -> Either.fromNullable(null, null));
        assertThrows(IllegalArgumentException.class, () -> Either.fromNullable("a", "b"));
    }

    @Test
    void testNullValues()
    {
        Either<String, Object> e = Either.first(null);
        assertTrue(e.isFirst());
        assertNull(e.getFirst().unwrap());
    }

    @Test
    void testInverseTwice()
    {
        Either<String, Integer> e = Either.first("hello");
        assertEquals("hello", e.inverse().inverse().getFirst().unwrap());
    }
}
