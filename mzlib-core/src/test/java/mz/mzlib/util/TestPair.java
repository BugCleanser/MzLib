package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestPair
{
    @Test
    void testOf()
    {
        Pair<String, Integer> p = Pair.of("hello", 42);
        assertEquals("hello", p.getFirst());
        assertEquals(42, p.getSecond());
    }

    @Test
    void testEqualsAndHashCode()
    {
        Pair<String, Integer> p1 = Pair.of("hello", 42);
        Pair<String, Integer> p2 = Pair.of("hello", 42);
        Pair<String, Integer> p3 = Pair.of("world", 42);
        Pair<String, Integer> p4 = Pair.of("hello", 99);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());

        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
        assertNotEquals(p1, "not a pair");
    }

    @Test
    void testToString()
    {
        String s = Pair.of("hello", 42).toString();
        assertTrue(s.contains("hello"));
        assertTrue(s.contains("42"));
    }

    @Test
    void testToMapEntry()
    {
        Map.Entry<String, Integer> entry = Pair.of("hello", 42).toMapEntry();
        assertEquals("hello", entry.getKey());
        assertEquals(42, entry.getValue());
    }

    @Test
    void testMut()
    {
        Pair.Mut<String, Integer> m = Pair.Mut.of("hello", 42);
        assertEquals("hello", m.getFirst());
        assertEquals(42, m.getSecond());

        m.setFirst("world");
        m.setSecond(99);
        assertEquals("world", m.getFirst());
        assertEquals(99, m.getSecond());
    }

    @Test
    void testComparator()
    {
        Comparator<Pair<String, Integer>> cmp = Pair.comparing();

        Pair<String, Integer> p1 = Pair.of("a", 1);
        Pair<String, Integer> p2 = Pair.of("a", 2);
        Pair<String, Integer> p3 = Pair.of("b", 1);

        assertTrue(cmp.compare(p1, p2) < 0);
        assertTrue(cmp.compare(p1, p3) < 0);
        assertTrue(cmp.compare(p2, p3) < 0);
        assertEquals(0, cmp.compare(p1, Pair.of("a", 1)));
    }

    @Test
    void testComparingByFirst()
    {
        Comparator<Pair<Integer, String>> cmp = Pair.comparingByFirst();
        assertTrue(cmp.compare(Pair.of(1, "a"), Pair.of(2, "b")) < 0);
        assertEquals(0, cmp.compare(Pair.of(1, "a"), Pair.of(1, "b")));
    }

    @Test
    void testComparingBySecond()
    {
        Comparator<Pair<String, Integer>> cmp = Pair.comparingBySecond();
        assertTrue(cmp.compare(Pair.of("a", 1), Pair.of("b", 2)) < 0);
        assertEquals(0, cmp.compare(Pair.of("a", 1), Pair.of("b", 1)));
    }

    @Test
    void testComparingByFirstCustom()
    {
        Comparator<String> reverseStr = Comparator.reverseOrder();
        Comparator<Pair<String, Integer>> cmp = Pair.comparingByFirst(reverseStr);
        assertTrue(cmp.compare(Pair.of("b", 1), Pair.of("a", 2)) < 0);
    }

    @Test
    void testComparingBySecondCustom()
    {
        Comparator<Integer> reverseInt = Comparator.reverseOrder();
        Comparator<Pair<String, Integer>> cmp = Pair.comparingBySecond(reverseInt);
        assertTrue(cmp.compare(Pair.of("a", 2), Pair.of("b", 1)) < 0);
    }

    @Test
    void testNullValues()
    {
        Pair<String, String> p = Pair.of(null, null);
        assertNull(p.getFirst());
        assertNull(p.getSecond());
        assertEquals(Pair.of(null, null), Pair.of(null, null));
    }
}
