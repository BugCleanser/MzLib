package mz.mzlib.util;

import org.junit.jupiter.api.Test;

public class TestTypedMap
{
    @Test
    public void test()
    {
        TypedMap<Object, Object> map = new TypedMap<>();
        TypedMap.KeySafe<Object, String> ks = new TypedMap.KeySafe<>(String.class, new Object());
        TypedMap.KeySafe<Object, String> ks1 = new TypedMap.KeySafe<>(String.class, new Object());
        TypedMap.KeySafe<Object, Integer> ki = new TypedMap.KeySafe<>(Integer.class, new Object());
        map.put(ks, "string");
        map.put(ks1, "string1");
        map.put(ki, 1);
        System.out.println(map.get(ks));
        System.out.println(map.get(ks1));
        System.out.println(map.get(ki));
    }

    @Test
    public void testClass()
    {
        TypedMap<Unit, Object> map = new TypedMap<>();
        map.put(new TypedMap.KeySafe<>(String.class, Unit.INSTANCE), "string");
        map.put(new TypedMap.KeySafe<>(Integer.class, Unit.INSTANCE), 1);
        System.out.println(map.get(new TypedMap.KeySafe<>(String.class, Unit.INSTANCE)));
        System.out.println(map.get(new TypedMap.KeySafe<>(Integer.class, Unit.INSTANCE)));
    }
}
