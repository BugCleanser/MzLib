package mz.mzlib.util;

import mz.mzlib.util.proxy.IteratorProxy;
import mz.mzlib.util.proxy.MapProxy;

import java.util.*;
import java.util.function.Function;

public class ClassCache<K extends Class<?>, V> implements Iterable<Map.Entry<K, V>>
{
    Map<K, RefWeak<V>> delegate = new MapConcurrentWeakHash<>();
    Function<? super K, ? extends V> initializer;
    public ClassCache(Function<? super K, ? extends V> initializer)
    {
        this.initializer = initializer;
    }

    RefWeak<V> value(K clazz, V value)
    {
        ClassUtil.makeReference(clazz.getClassLoader(), value);
        return new RefWeak<>(value);
    }
    RefWeak<V> value(K clazz)
    {
        return this.value(clazz, this.initializer.apply(clazz));
    }

    public V get(K clazz)
    {
        return Objects.requireNonNull(this.delegate.computeIfAbsent(clazz, this::value).get());
    }

    public Option<V> put(K clazz, V value)
    {
        return Option.fromNullable(this.delegate.put(clazz, this.value(clazz, value))).map(Ref::get);
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator()
    {
        return new IteratorProxy<>(
            this.delegate.entrySet().iterator(),
            e -> new MapProxy.EntryProxy<>(e, Function.identity(), FunctionInvertible.of(RefWeak::get, RefWeak::new))
        );
    }
}
