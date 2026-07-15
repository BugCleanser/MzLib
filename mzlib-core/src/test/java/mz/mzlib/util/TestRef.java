package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRef
{
    @Test
    void testRefStrongGetSet()
    {
        RefStrong<String> r = RefStrong.of("hello");
        assertEquals("hello", r.get());
        r.set("world");
        assertEquals("world", r.get());
    }

    @Test
    void testRefStrongOfNull()
    {
        RefStrong<String> r = RefStrong.ofNull();
        assertNull(r.get());
    }

    @Test
    void testRefStrongEquals()
    {
        String shared = "hello";
        RefStrong<String> r1 = RefStrong.of(shared);
        RefStrong<String> r2 = RefStrong.of(shared);

        assertEquals(r1, r2);
    }

    @Test
    void testRefStrongHashCode()
    {
        String value = "hello";
        assertEquals(System.identityHashCode(value), RefStrong.of(value).hashCode());
    }

    @Test
    void testRefStrongToString()
    {
        assertEquals("hello", RefStrong.of("hello").toString());
        assertEquals("null", RefStrong.ofNull().toString());
    }

    @Test
    void testRefStrongIdentityComparison()
    {
        // Ref Strong uses identity (==) for equality, not equals()
        String a = new String("hello");
        String b = new String("hello");
        assertNotEquals(RefStrong.of(a), RefStrong.of(b));
    }

    @Test
    void testRefWeakGetSet()
    {
        RefWeak<String> r = new RefWeak<>("hello");
        assertEquals("hello", r.get());
        r.set("world");
        assertEquals("world", r.get());
    }

    @Test
    void testRefWeakHashStableAfterGc()
    {
        // hashCode is captured at set time, stays stable even if referent gets GC'd
        String value = "test";
        RefWeak<String> r = new RefWeak<>(value);
        int hc = r.hashCode();

        // trigger GC on the referent
        value = null;
        System.gc();

        assertEquals(hc, r.hashCode());
    }

    @Test
    void testRefWeakNullValue()
    {
        RefWeak<String> r = new RefWeak<>(null);
        assertNull(r.get());
    }

    @Test
    void testRefMap()
    {
        Ref<String> r = RefStrong.of("hello");
        Ref<Integer> mapped = r.map(String::length);
        assertEquals(5, mapped.get());
    }

    @Test
    void testGetOrSet()
    {
        Ref<Option<String>> ref = RefStrong.of(Option.none());
        String result = Ref.getOrSet(ref, () -> "default");
        assertEquals("default", result);
        assertEquals("default", ref.get().unwrap());
    }

    @Test
    void testGetOrSetWhenPresent()
    {
        Ref<Option<String>> ref = RefStrong.of(Option.some("existing"));
        String result = Ref.getOrSet(ref, () -> "default");
        assertEquals("existing", result);
    }
}
