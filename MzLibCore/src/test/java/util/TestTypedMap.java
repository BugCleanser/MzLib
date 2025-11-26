package util;

import mz.mzlib.util.Option;
import mz.mzlib.util.TypedMap;
import org.junit.jupiter.api.Test;

public class TestTypedMap
{
    @Test
    public void test()
    {
        TypedMap<AbstractTestKey<?>> t = TypedMap.of();
        t.asMap().put(new TestKey<String>(), "String");
        t.put(new TestKey<String>(), "");
        t.put(new TestKey<>(), 1);
        Option<String> s = t.get(new TestKey<String>());
    }

    static abstract class AbstractTestKey<T> implements TypedMap.Key<T, AbstractTestKey<?>>
    {
    }
    static class TestKey<T> extends AbstractTestKey<T>
    {
    }
}
