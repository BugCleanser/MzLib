package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestBox
{
    @Test
    void testGet()
    {
        Box.Mut<String> b = Box.of("hello");
        assertEquals("hello", b.get());
    }

    @Test
    void testSet()
    {
        Box.Mut<String> b = Box.of("hello");
        b.set("world");
        assertEquals("world", b.get());
    }

    @Test
    void testMap()
    {
        Box.Mut<String> b = Box.of("hello").map(String::toUpperCase);
        assertEquals("HELLO", b.get());
    }

    @Test
    void testEqualsAndHashCode()
    {
        assertEquals(Box.of("hello"), Box.of("hello"));
        assertEquals(Box.of("hello").hashCode(), Box.of("hello").hashCode());
        assertNotEquals(Box.of("hello"), Box.of("world"));
        assertNotEquals(Box.of("hello"), "hello");
    }

    @Test
    void testToString()
    {
        assertTrue(Box.of("hello").toString().contains("hello"));
    }

    @Test
    void testNullValue()
    {
        Box.Mut<String> b = Box.of(null);
        assertNull(b.get());
        assertEquals(Box.of(null), Box.of(null));
    }

    @Test
    void testNonMutBox()
    {
        Box<String> b = new Box<>("hello");
        assertEquals("hello", b.get());
    }
}
