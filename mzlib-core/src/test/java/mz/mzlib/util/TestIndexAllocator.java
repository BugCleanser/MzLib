package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestIndexAllocator
{
    @Test
    void testAlloc()
    {
        IndexAllocator<String> a = new IndexAllocator<>();
        assertEquals(0, a.alloc());
        assertEquals(1, a.alloc());
        assertEquals(2, a.alloc());
        assertEquals(3, a.size());
    }

    @Test
    void testAllocWithValue()
    {
        IndexAllocator<String> a = new IndexAllocator<>();
        assertEquals(0, a.alloc("first"));
        assertEquals(1, a.alloc("second"));
        assertEquals("first", a.get(0));
        assertEquals("second", a.get(1));
    }

    @Test
    void testFreeAndReuse()
    {
        IndexAllocator<String> a = new IndexAllocator<>();
        a.alloc("a");
        a.alloc("b");
        a.alloc("c");

        a.free(1);
        assertEquals(2, a.size());

        int reusedIndex = a.alloc("reused");
        assertEquals(1, reusedIndex);
        assertEquals("reused", a.get(1));
        assertEquals(3, a.size());
    }

    @Test
    void testFreeMultipleReuse()
    {
        IndexAllocator<String> a = new IndexAllocator<>();
        a.alloc("a");
        a.alloc("b");
        a.alloc("c");

        a.free(2);
        a.free(0);

        // bin is a Queue (FIFO), so freed indices return in order: 2 then 0
        assertEquals(2, a.alloc());
        assertEquals(0, a.alloc());
    }

    @Test
    void testSet()
    {
        IndexAllocator<String> a = new IndexAllocator<>();
        a.alloc("original");
        a.set(0, "modified");
        assertEquals("modified", a.get(0));
    }

    @Test
    void testAllocNullValue()
    {
        IndexAllocator<String> a = new IndexAllocator<>();
        assertEquals(0, a.alloc());
        assertNull(a.get(0));
    }

    @Test
    void testSize()
    {
        IndexAllocator<String> a = new IndexAllocator<>();
        assertEquals(0, a.size());
        a.alloc();
        assertEquals(1, a.size());
        a.alloc();
        assertEquals(2, a.size());
        a.free(0);
        assertEquals(1, a.size());
    }
}
