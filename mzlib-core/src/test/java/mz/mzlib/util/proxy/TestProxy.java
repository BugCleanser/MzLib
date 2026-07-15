package mz.mzlib.util.proxy;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.ModifyMonitor;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class TestProxy
{
    @Test
    void testCollectionProxySize()
    {
        Collection<String> c = CollectionProxy.of(
            new ArrayList<>(Arrays.asList("a", "b")),
            FunctionInvertible.identity()
        );
        assertEquals(2, c.size());
    }

    @Test
    void testCollectionProxyIsEmpty()
    {
        Collection<String> c = CollectionProxy.of(
            new ArrayList<>(),
            FunctionInvertible.identity()
        );
        assertTrue(c.isEmpty());
    }

    @Test
    void testCollectionProxyContains()
    {
        Collection<String> c = CollectionProxy.of(
            new ArrayList<>(Arrays.asList("a", "b")),
            FunctionInvertible.identity()
        );
        assertTrue(c.contains("a"));
        assertFalse(c.contains("c"));
    }

    @Test
    void testCollectionProxyAdd()
    {
        List<String> delegate = new ArrayList<>();
        Collection<String> c = CollectionProxy.of(delegate, FunctionInvertible.identity());
        c.add("x");
        assertEquals(1, delegate.size());
        assertEquals("x", delegate.get(0));
    }

    @Test
    void testCollectionProxyRemove()
    {
        List<String> delegate = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> c = CollectionProxy.of(delegate, FunctionInvertible.identity());
        assertTrue(c.remove("a"));
        assertEquals(1, delegate.size());
        assertEquals("b", delegate.get(0));
        assertFalse(c.remove("c"));
    }

    @Test
    void testCollectionProxyClear()
    {
        List<String> delegate = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> c = CollectionProxy.of(delegate, FunctionInvertible.identity());
        c.clear();
        assertTrue(delegate.isEmpty());
    }

    @Test
    void testCollectionProxyIterator()
    {
        Collection<String> c = CollectionProxy.of(
            new ArrayList<>(Arrays.asList("a", "b")),
            FunctionInvertible.identity()
        );
        List<String> result = new ArrayList<>();
        for(String s : c)
            result.add(s);
        assertEquals(Arrays.asList("a", "b"), result);
    }

    @Test
    void testCollectionProxyWithTransform()
    {
        // Transform: internal Integer -> external String
        List<Integer> delegate = new ArrayList<>(Arrays.asList(1, 2, 3));
        FunctionInvertible<Integer, String> transform = FunctionInvertible.of(
            i -> "n:" + i,
            s -> Integer.parseInt(s.substring(2))
        );
        Collection<String> c = CollectionProxy.of(delegate, transform);

        assertEquals(3, c.size());
        assertTrue(c.contains("n:1"));
        assertFalse(c.contains("n:999"));

        c.add("n:4");
        assertEquals(4, delegate.size());
        assertTrue(delegate.contains(4));
    }

    @Test
    void testCollectionProxyModifyMonitor()
    {
        AtomicInteger modifyCount = new AtomicInteger(0);
        AtomicInteger dirtyCount = new AtomicInteger(0);
        ModifyMonitor monitor = new ModifyMonitor.Simple(modifyCount::incrementAndGet, dirtyCount::incrementAndGet);

        Collection<String> c = CollectionProxy.of(
            new ArrayList<>(),
            FunctionInvertible.identity(),
            monitor
        );

        c.add("x");
        assertEquals(1, modifyCount.get());
        assertEquals(1, dirtyCount.get());

        c.addAll(Arrays.asList("y", "z"));
        assertEquals(2, modifyCount.get());
        assertEquals(2, dirtyCount.get());
    }

    @Test
    void testListProxy()
    {
        List<Integer> delegate = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ListProxy<String, Integer> list = new ListProxy<>(
            delegate,
            FunctionInvertible.of(i -> "v" + i, s -> Integer.parseInt(s.substring(1)))
        );

        assertEquals("v1", list.get(0));
        assertEquals("v3", list.get(2));
        assertEquals(5, list.size());

        assertEquals(2, list.indexOf("v3"));
        assertEquals(-1, list.indexOf("v999"));
    }

    @Test
    void testListProxySet()
    {
        List<Integer> delegate = new ArrayList<>(Arrays.asList(1, 2));
        ListProxy<String, Integer> list = new ListProxy<>(
            delegate,
            FunctionInvertible.of(i -> "v" + i, s -> Integer.parseInt(s.substring(1)))
        );

        String old = list.set(0, "v99");
        assertEquals("v1", old);
        assertEquals(99, delegate.get(0).intValue());
    }

    @Test
    void testListProxySubList()
    {
        List<Integer> delegate = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListProxy<Integer, Integer> list = new ListProxy<>(
            delegate,
            FunctionInvertible.identity()
        );

        List<Integer> sub = list.subList(0, 2);
        assertEquals(2, sub.size());
        assertEquals(1, sub.get(0));
        assertEquals(2, sub.get(1));
    }

    @Test
    void testMapProxy()
    {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("a", 1);
        delegate.put("b", 2);

        MapProxy<String, Integer, String, Integer> map = new MapProxy<>(
            delegate,
            FunctionInvertible.identity(),
            FunctionInvertible.identity()
        );

        assertEquals(2, map.size());
        assertEquals(1, map.get("a"));
        assertTrue(map.containsKey("b"));
        assertFalse(map.containsKey("c"));
        assertTrue(map.containsValue(1));
    }

    @Test
    void testMapProxyPut()
    {
        Map<String, Integer> delegate = new HashMap<>();
        MapProxy<String, Integer, String, Integer> map = new MapProxy<>(
            delegate,
            FunctionInvertible.identity(),
            FunctionInvertible.identity()
        );

        map.put("key", 42);
        assertEquals(42, delegate.get("key"));
    }

    @Test
    void testMapProxyRemove()
    {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("a", 1);
        delegate.put("b", 2);

        MapProxy<String, Integer, String, Integer> map = new MapProxy<>(
            delegate,
            FunctionInvertible.identity(),
            FunctionInvertible.identity()
        );

        assertEquals(1, map.remove("a"));
        assertFalse(delegate.containsKey("a"));
        assertEquals(1, delegate.size());
    }

    @Test
    void testMapProxyClear()
    {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("a", 1);

        MapProxy<String, Integer, String, Integer> map = new MapProxy<>(
            delegate,
            FunctionInvertible.identity(),
            FunctionInvertible.identity()
        );

        map.clear();
        assertTrue(delegate.isEmpty());
    }

    @Test
    void testMapProxyKeySet()
    {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("a", 1);
        delegate.put("b", 2);

        MapProxy<String, Integer, String, Integer> map = MapProxy.of(delegate, ModifyMonitor.Empty.instance);

        Set<String> keys = map.keySet();
        assertEquals(2, keys.size());
        assertTrue(keys.contains("a"));
    }

    @Test
    void testMapProxyValues()
    {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("a", 1);
        delegate.put("b", 2);

        MapProxy<String, Integer, String, Integer> map = MapProxy.of(delegate, ModifyMonitor.Empty.instance);

        Collection<Integer> values = map.values();
        assertEquals(2, values.size());
        assertTrue(values.contains(1));
    }

    @Test
    void testMapProxyEntrySet()
    {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("a", 1);

        MapProxy<String, Integer, String, Integer> map = MapProxy.of(delegate, ModifyMonitor.Empty.instance);

        for(Map.Entry<String, Integer> entry : map.entrySet())
        {
            assertEquals("a", entry.getKey());
            assertEquals(1, entry.getValue());
        }
    }

    @Test
    void testMapProxyWithTransform()
    {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("a", 1);

        // Transform keys: prefix with "x", values: multiply by 10
        MapProxy<String, Integer, String, Integer> map = new MapProxy<>(
            delegate,
            FunctionInvertible.of(k -> "x" + k, k -> k.substring(1)),
            FunctionInvertible.of(v -> v * 10, v -> v / 10)
        );

        assertEquals(10, map.get("xa"));
        assertTrue(map.containsKey("xa"));
        assertFalse(map.containsKey("a"));
        assertTrue(map.containsValue(10));
    }

    @Test
    void testMapProxyPutAll()
    {
        Map<String, Integer> delegate = new HashMap<>();
        MapProxy<String, Integer, String, Integer> map = new MapProxy<>(
            delegate,
            FunctionInvertible.identity(),
            FunctionInvertible.identity()
        );

        Map<String, Integer> source = new HashMap<>();
        source.put("x", 1);
        source.put("y", 2);
        map.putAll(source);

        assertEquals(2, delegate.size());
        assertEquals(1, delegate.get("x"));
    }

    @Test
    void testEntryProxy()
    {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("key", 42);

        Map.Entry<String, Integer> delegateEntry = delegate.entrySet().iterator().next();
        MapProxy.EntryProxy<String, Integer, String, Integer> entry = new MapProxy.EntryProxy<>(
            delegateEntry,
            FunctionInvertible.identity(),
            FunctionInvertible.identity()
        );

        assertEquals("key", entry.getKey());
        assertEquals(42, entry.getValue());
        // EntryProxy and HashMap$Node use different hashCode algorithms
        // but they should be equal via Map.Entry contract
        assertEquals(delegateEntry.getKey(), entry.getKey());
        assertEquals(delegateEntry.getValue(), entry.getValue());
        assertTrue(entry.equals(delegateEntry));
    }

    @Test
    void testEntryProxySetValue()
    {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("key", 42);

        Map.Entry<String, Integer> delegateEntry = delegate.entrySet().iterator().next();
        MapProxy.EntryProxy<String, Integer, String, Integer> entry = new MapProxy.EntryProxy<>(
            delegateEntry,
            FunctionInvertible.identity(),
            FunctionInvertible.identity()
        );

        entry.setValue(100);
        assertEquals(100, delegate.get("key"));
    }
}
