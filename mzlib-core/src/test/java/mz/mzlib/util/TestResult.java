package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestResult
{
    @Test
    void testSuccess()
    {
        Result<String, Integer> r = Result.success("hello");
        assertTrue(r.isSuccess());
        assertFalse(r.isFailure());
        assertEquals("hello", r.getValue());
        assertNull(r.getError());
        assertTrue(r.getPossibleValue().isSome());
        assertTrue(r.getPossibleError().isNone());
    }

    @Test
    void testFailure()
    {
        Result<String, Integer> r = Result.failure(404);
        assertFalse(r.isSuccess());
        assertTrue(r.isFailure());
        assertNull(r.getValue());
        assertEquals(404, r.getError().intValue());
        assertTrue(r.getPossibleValue().isNone());
        assertTrue(r.getPossibleError().isSome());
    }

    @Test
    void testFailureWithValue()
    {
        Result<String, Integer> r = Result.failure(Option.some("partial"), 500);
        assertFalse(r.isSuccess());
        assertTrue(r.isFailure());
        assertEquals("partial", r.getValue());
        assertEquals(500, r.getError().intValue());
    }

    @Test
    void testIsSuccessWithValue()
    {
        Result<String, Integer> r = Result.success("hello");
        assertTrue(r.isSuccess("hello"));
        assertFalse(r.isSuccess("world"));
    }

    @Test
    void testIsFailureWithError()
    {
        Result<String, Integer> r = Result.failure(404);
        assertTrue(r.isFailure(404));
        assertFalse(r.isFailure(500));
    }

    @Test
    void testToEither()
    {
        Result<String, Integer> r1 = Result.success("hello");
        Either<String, Integer> e1 = r1.toEither();
        assertTrue(e1.isFirst());
        assertEquals("hello", e1.getFirst().unwrap());

        Result<String, Integer> r2 = Result.failure(404);
        Either<String, Integer> e2 = r2.toEither();
        assertTrue(e2.isSecond());
        assertEquals(404, e2.getSecond().unwrap());
    }

    @Test
    void testToPair()
    {
        Result<String, Integer> r = Result.success("hello");
        assertEquals("hello", r.toPair().getFirst());
        assertNull(r.toPair().getSecond());

        Result<String, Integer> r2 = Result.failure(404);
        assertNull(r2.toPair().getFirst());
        assertEquals(404, r2.toPair().getSecond());
    }

    @Test
    void testMapValue()
    {
        Result<Integer, String> r1 = Result.success(5);
        Result<String, String> mapped1 = r1.mapValue(i -> "n=" + i);
        assertTrue(mapped1.isSuccess());
        assertEquals("n=5", mapped1.getValue());

        Result<Integer, String> r2 = Result.failure("err");
        Result<String, String> mapped2 = r2.mapValue(i -> "n=" + i);
        assertTrue(mapped2.isFailure());
        assertEquals("err", mapped2.getError());
    }

    @Test
    void testMapError()
    {
        Result<String, Integer> r1 = Result.success("hello");
        Result<String, String> mapped1 = r1.mapError(e -> "code:" + e);
        assertTrue(mapped1.isSuccess());

        Result<String, Integer> r2 = Result.failure(404);
        Result<String, String> mapped2 = r2.mapError(e -> "code:" + e);
        assertTrue(mapped2.isFailure());
        assertEquals("code:404", mapped2.getError());
    }

    @Test
    void testGetOrThrow()
    {
        Result<String, Integer> r1 = Result.success("hello");
        assertEquals("hello", r1.getOrThrow(e -> new RuntimeException("error:" + e)));

        Result<String, Integer> r2 = Result.failure(404);
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            r2.getOrThrow(e -> new RuntimeException("error:" + e)));
        assertTrue(ex.getMessage().contains("404"));
    }

    @Test
    void testOf()
    {
        Result<String, Integer> r1 = Result.of(Option.some("hello"), Option.none());
        assertTrue(r1.isSuccess());
        assertEquals("hello", r1.getValue());

        Result<String, Integer> r2 = Result.of(Option.none(), Option.some(404));
        assertTrue(r2.isFailure());
        assertEquals(404, r2.getError().intValue());

        Result<String, Integer> r3 = Result.of(Option.some("partial"), Option.some(500));
        assertTrue(r3.isFailure());
        assertEquals("partial", r3.getValue());
        assertEquals(500, r3.getError().intValue());

        assertThrows(IllegalArgumentException.class, () ->
            Result.of(Option.none(), Option.none()));
    }

    @Test
    void testToString()
    {
        assertTrue(Result.success("hello").toString().contains("hello"));
        assertTrue(Result.failure(404).toString().contains("404"));
    }
}
