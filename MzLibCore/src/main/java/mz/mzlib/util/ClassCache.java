package mz.mzlib.util;

import mz.mzlib.util.proxy.IteratorProxy;
import mz.mzlib.util.proxy.MapProxy;

import java.util.*;
import java.util.function.Function;

public class ClassCache<K extends Class<?>, V> implements Iterable<Map.Entry<K, V>>
{
    Map<K, RefWeak<V>> delegate;
    Function<? super K, ? extends V> initializer;
    ClassCache(Map<K, RefWeak<V>> delegate, Function<? super K, ? extends V> initializer)
    {
        this.delegate = delegate;
        this.initializer = initializer;
    }
    public ClassCache(Function<Class<?>, V> initializer)
    {
        this(new WeakHashMap<>(), initializer);
    }
    public static <K extends Class<?>, V> ClassCache<K, V> sync(Function<? super K, ? extends V> initializer)
    {
        return new ClassCache<>(Collections.synchronizedMap(new WeakHashMap<>()), initializer);
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
