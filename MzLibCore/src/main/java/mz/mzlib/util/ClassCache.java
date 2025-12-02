package mz.mzlib.util;

import mz.mzlib.util.proxy.IteratorProxy;
import mz.mzlib.util.proxy.MapProxy;

import java.util.*;
import java.util.function.Function;

public class ClassCache<T> implements Iterable<Map.Entry<Class<?>, T>>
{
    WeakHashMap<Class<?>, RefWeak<T>> delegate = new WeakHashMap<>();
    Function<Class<?>, T> initializer;
    public ClassCache(Function<Class<?>, T> initializer)
    {
        this.initializer = initializer;
    }

    RefWeak<T> value(Class<?> clazz, T value)
    {
        ClassUtil.makeReference(clazz.getClassLoader(), value);
        return new RefWeak<>(value);
    }
    RefWeak<T> value(Class<?> clazz)
    {
        return this.value(clazz, this.initializer.apply(clazz));
    }

    public T get(Class<?> clazz)
    {
        return Objects.requireNonNull(this.delegate.computeIfAbsent(clazz, this::value).get());
    }

    public Option<T> put(Class<?> clazz, T value)
    {
        return Option.fromNullable(this.delegate.put(clazz, this.value(clazz, value))).map(Ref::get);
    }

    @Override
    public Iterator<Map.Entry<Class<?>, T>> iterator()
    {
        return new IteratorProxy<>(
            this.delegate.entrySet().iterator(),
            e -> new MapProxy.EntryProxy<>(e, Function.identity(), FunctionInvertible.of(RefWeak::get, RefWeak::new))
        );
    }
}
