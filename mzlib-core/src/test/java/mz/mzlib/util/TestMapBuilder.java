package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestMapBuilder
{
    @Test
    void testBuild()
    {
        Map<String, Integer> map = MapBuilder.<Map<String, Integer>>hashMap()
            .put("one", 1)
            .put("two", 2)
            .get();

        assertEquals(2, map.size());
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
    }

    @Test
    void testChaining()
    {
        MapBuilder builder = MapBuilder.hashMap()
            .put("a", 1)
            .put("b", 2)
            .put("c", 3);
        assertSame(builder, builder.put("d", 4));

        Map<String, Integer> map = builder.get();
        assertEquals(4, map.size());
    }

    @Test
    void testEmpty()
    {
        Map<Object, Object> map = MapBuilder.hashMap().get();
        assertTrue(map.isEmpty());
    }

    @Test
    void testWithExistingMap()
    {
        Map<String, Integer> existing = new HashMap<>();
        existing.put("pre", 99);
        Map<String, Integer> map = new MapBuilder(existing)
            .put("one", 1)
            .get();

        assertEquals(2, map.size());
        assertEquals(99, map.get("pre"));
        assertEquals(1, map.get("one"));
    }

    @Test
    void testOverrideKey()
    {
        Map<String, Integer> map = MapBuilder.<Map<String, Integer>>hashMap()
            .put("key", 1)
            .put("key", 2)
            .get();

        assertEquals(1, map.size());
        assertEquals(2, map.get("key"));
    }
}
