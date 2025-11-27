package util;

import mz.mzlib.util.TypedMap;
import mz.mzlib.util.TypedMapClass;
import org.junit.jupiter.api.Test;

public class TestTypedMap
{
    @Test
    public void test()
    {
        TypedMap<TestKey<?>> map = TypedMap.of();
        TestKey<String> ks = new TestKey<>();
        TestKey1<String> ks1 = new TestKey1<>();
        TestKey1<Integer> ki1 = new TestKey1<>();
        map.put(ks, "string");
        map.put(ks1, "string1");
        map.put(ki1, 1);
        System.out.println(map.get(ks).unwrap());
        System.out.println(map.get(ks1).unwrap());
        System.out.println(map.get(ki1).unwrap());
    }

    @Test
    public void testClass()
    {
        TypedMapClass map = TypedMapClass.of();
        map.put(String.class, "string");
        map.put(Integer.class, 1);
        System.out.println(map.get(String.class).unwrap());
        System.out.println(map.get(Integer.class).unwrap());
    }

    static class TestKey<T> implements TypedMap.Key<T, TestKey<?>>
    {
    }
    static class TestKey1<T> extends TestKey<T>
    {
    }
}
