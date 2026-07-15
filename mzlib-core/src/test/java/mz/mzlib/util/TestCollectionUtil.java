package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TestCollectionUtil
{
    @Test
    void testReverse()
    {
        List<Integer> result = CollectionUtil.reverse(Stream.of(1, 2, 3)).collect(Collectors.toList());
        assertEquals(Arrays.asList(3, 2, 1), result);
    }

    @Test
    void testNewArrayList()
    {
        List<String> list = CollectionUtil.newArrayList("a", "b", "c");
        assertEquals(Arrays.asList("a", "b", "c"), list);
        assertInstanceOf(ArrayList.class, list);
    }

    @Test
    void testNewArrayListFromIterable()
    {
        Set<String> set = new LinkedHashSet<>(Arrays.asList("a", "b"));
        List<String> list = CollectionUtil.newArrayList(set);
        assertEquals(Arrays.asList("a", "b"), list);
    }

    @Test
    void testNewHashMap()
    {
        HashMap<String, Integer> map = CollectionUtil.newHashMap("key", 42);
        assertEquals(1, map.size());
        assertEquals(42, map.get("key"));
    }

    @Test
    void testAddAll()
    {
        List<String> list = new ArrayList<>(Arrays.asList("a"));
        CollectionUtil.addAll(list, "b", "c");
        assertEquals(Arrays.asList("a", "b", "c"), list);
    }

    @Test
    void testReplace()
    {
        List<Object> result = CollectionUtil.replace("hello {name}!", "{name}", "world");
        assertEquals(Arrays.asList("hello ", "world", "!"), result);
    }

    @Test
    void testReplaceMultiple()
    {
        List<Object> result = CollectionUtil.replace("a,a,a", ",", "|");
        assertEquals(Arrays.asList("a", "|", "a", "|", "a"), result);
    }

    @Test
    void testReplaceNoMatch()
    {
        List<Object> result = CollectionUtil.replace("hello", "x", "y");
        assertEquals(Arrays.asList("hello"), result);
    }

    @Test
    void testSplit()
    {
        List<List<Integer>> result = CollectionUtil.split(
            Arrays.asList(1, 2, 0, 3, 0, 4), 0);
        assertEquals(Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(3),
            Arrays.asList(4)
        ), result);
    }

    @Test
    void testSplitNoSeparator()
    {
        List<List<Integer>> result = CollectionUtil.split(Arrays.asList(1, 2, 3), 0);
        assertEquals(Arrays.asList(Arrays.asList(1, 2, 3)), result);
    }

    @Test
    void testSplitAllSeparators()
    {
        List<List<Integer>> result = CollectionUtil.split(Arrays.asList(0, 0), 0);
        assertEquals(Arrays.asList(
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList()
        ), result);
    }

    @Test
    void testToObjectArray()
    {
        Object array = new int[]{1, 2, 3};
        Object[] result = CollectionUtil.toObjectArray(array);
        assertEquals(3, result.length);
        assertEquals(1, result[0]);
        assertEquals(2, result[1]);
        assertEquals(3, result[2]);
    }

    @Test
    void testToObjectArrayObjectArray()
    {
        String[] array = {"a", "b"};
        Object[] result = CollectionUtil.toObjectArray(array);
        assertSame(array, result);
    }

    @Test
    void testAsIterable()
    {
        Iterator<String> it = Arrays.asList("a", "b").iterator();
        Iterable<String> iterable = CollectionUtil.asIterable(it);
        List<String> result = new ArrayList<>();
        for(String s : iterable)
            result.add(s);
        assertEquals(Arrays.asList("a", "b"), result);
    }

    @Test
    void testEach()
    {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        for(Ref<String> ref : CollectionUtil.each(list))
        {
            if("b".equals(ref.get()))
                ref.set("B");
        }
        assertEquals(Arrays.asList("a", "B", "c"), list);
    }
}
