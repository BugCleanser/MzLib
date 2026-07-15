package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestFunctionInvertible
{
    @Test
    void testApply()
    {
        FunctionInvertible<Integer, String> f = FunctionInvertible.of(
            i -> "n:" + i,
            s -> Integer.parseInt(s.substring(2))
        );
        assertEquals("n:42", f.apply(42));
    }

    @Test
    void testInverse()
    {
        FunctionInvertible<Integer, String> f = FunctionInvertible.of(
            i -> "n:" + i,
            s -> Integer.parseInt(s.substring(2))
        );
        FunctionInvertible<String, Integer> inv = f.inverse();
        assertEquals(42, inv.apply("n:42"));
    }

    @Test
    void testInverseRoundTrip()
    {
        FunctionInvertible<Integer, String> f = FunctionInvertible.of(
            i -> "n:" + i,
            s -> Integer.parseInt(s.substring(2))
        );
        assertEquals("n:42", f.inverse().inverse().apply(42));
        assertEquals(42, f.inverse().inverse().inverse().apply("n:42"));
    }

    @Test
    void testIdentity()
    {
        FunctionInvertible<String, String> id = FunctionInvertible.identity();
        assertEquals("hello", id.apply("hello"));
        assertEquals("hello", id.inverse().apply("hello"));
    }

    @Test
    void testThenApply()
    {
        FunctionInvertible<Integer, String> f1 = FunctionInvertible.of(
            i -> "n:" + i,
            s -> Integer.parseInt(s.substring(2))
        );
        // Properly invertible: string length <-> repeat that many 'x' chars
        FunctionInvertible<String, String> f2 = FunctionInvertible.of(
            s -> s + "!",
            s -> s.substring(0, s.length() - 1)
        );

        FunctionInvertible<Integer, String> composed = f1.thenApply(f2);
        assertEquals("n:42!", composed.apply(42));
        assertEquals(42, composed.inverse().apply("n:42!"));
    }

    @Test
    void testCast()
    {
        FunctionInvertible<Object, String> cast = FunctionInvertible.cast();
        assertEquals("hello", cast.apply("hello"));
        assertEquals("hello", cast.inverse().apply("hello"));
    }

    @Test
    void testRef()
    {
        FunctionInvertible<String, Ref<String>> f = FunctionInvertible.ref();
        Ref<String> ref = f.apply("hello");
        assertEquals("hello", ref.get());
        assertEquals("hello", f.inverse().apply(ref));
    }

    @Test
    void testOption()
    {
        FunctionInvertible<String, Option<String>> f = FunctionInvertible.option();
        assertTrue(f.apply("hello").isSome());
        assertTrue(f.apply(null).isNone());
    }

    @Test
    void testOptional()
    {
        FunctionInvertible<String, Optional<String>> f = FunctionInvertible.optional();
        assertTrue(f.apply("hello").isPresent());
        assertFalse(f.apply(null).isPresent());
    }
}
