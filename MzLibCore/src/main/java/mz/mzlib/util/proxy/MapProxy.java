package mz.mzlib.util.proxy;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.ModifyMonitor;
import mz.mzlib.util.RuntimeUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class MapProxy<K, V, K1, V1> implements Map<K, V>
{
    Map<K1, V1> delegate;
    FunctionInvertible<K1, K> functionKey;
    FunctionInvertible<V1, V> functionValue;
    ModifyMonitor modifyMonitor;

    public MapProxy(
        Map<K1, V1> delegate,
        FunctionInvertible<K1, K> functionKey,
        FunctionInvertible<V1, V> functionValue,
        ModifyMonitor modifyMonitor)
    {
        this.delegate = delegate;
        this.functionKey = functionKey;
        this.functionValue = functionValue;
        this.modifyMonitor = modifyMonitor;
    }
    public MapProxy(
        Map<K1, V1> delegate,
        FunctionInvertible<K1, K> functionKey,
        FunctionInvertible<V1, V> functionValue)
    {
        this(delegate, functionKey, functionValue, ModifyMonitor.Empty.instance);
    }
    public static <K, V> MapProxy<K, V, K, V> of(Map<K, V> delegate, ModifyMonitor modifyMonitor)
    {
        return new MapProxy<>(delegate, null, null, modifyMonitor);
    }

    @Override
    public int size()
    {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key)
    {
        K1 k1;
        try
        {
            k1 = functionKey.inverse().apply(RuntimeUtil.cast(key));
        }
        catch(ClassCastException e)
        {
            return false;
        }
        return this.delegate.containsKey(k1);
    }

    @Override
    public boolean containsValue(Object value)
    {
        V1 v1;
        try
        {
            v1 = functionValue.inverse().apply(RuntimeUtil.cast(value));
        }
        catch(ClassCastException e)
        {
            return false;
        }
        return this.delegate.containsValue(v1);
    }

    @Override
    public V get(Object key)
    {
        K1 k1;
        try
        {
            k1 = functionKey.inverse().apply(RuntimeUtil.cast(key));
        }
        catch(ClassCastException e)
        {
            return null;
        }
        V1 v1 = this.delegate.get(k1);
        if(v1 == null)
            return null;
        return functionValue.apply(v1);
    }

    @Override
    public V put(K key, V value)
    {
        this.modifyMonitor.onModify();
        V1 result = this.delegate.put(functionKey.inverse().apply(key), functionValue.inverse().apply(value));
        this.modifyMonitor.markDirty();
        if(result == null)
            return null;
        return functionValue.apply(result);
    }

    @Override
    public V remove(Object key)
    {
        K1 k1;
        try
        {
            k1 = functionKey.inverse().apply(RuntimeUtil.cast(key));
        }
        catch(ClassCastException e)
        {
            return null;
        }
        this.modifyMonitor.onModify();
        V1 result = this.delegate.remove(k1);
        this.modifyMonitor.markDirty();
        if(result == null)
            return null;
        return functionValue.apply(result);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        this.modifyMonitor.onModify();
        this.delegate.putAll(new MapProxy<>(RuntimeUtil.cast(m), functionKey.inverse(), functionValue.inverse()));
        this.modifyMonitor.markDirty();
    }

    @Override
    public void clear()
    {
        this.modifyMonitor.onModify();
        this.delegate.clear();
        this.modifyMonitor.markDirty();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Set<K> keySet()
    {
        return new SetProxy<>(this.delegate.keySet(), this.functionKey, this.modifyMonitor);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Collection<V> values()
    {
        return new CollectionProxy<>(this.delegate.values(), this.functionValue, this.modifyMonitor);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return new SetProxy<>(
            this.delegate.entrySet(), FunctionInvertible.of(
            e -> new EntryProxy<>(e, functionKey, functionValue),
            e -> new EntryProxy<>(e, functionKey.inverse(), functionValue.inverse())
        ), this.modifyMonitor
        );
    }

    public static class EntryProxy<K, V, K1, V1> implements Map.Entry<K, V>
    {
        Map.Entry<K1, V1> delegate;
        Function<K1, K> functionKey;
        FunctionInvertible<V1, V> functionValue;

        public EntryProxy(
            Map.Entry<K1, V1> delegate,
            Function<K1, K> functionKey,
            FunctionInvertible<V1, V> functionValue)
        {
            this.delegate = delegate;
            this.functionKey = functionKey;
            this.functionValue = functionValue;
        }

        @Override
        public K getKey()
        {
            return this.functionKey.apply(this.delegate.getKey());
        }

        @Override
        public V getValue()
        {
            return this.functionValue.apply(this.delegate.getValue());
        }

        @Override
        public V setValue(V value)
        {
            this.delegate.setValue(this.functionValue.inverse().apply(value));
            return null;
        }

        @Override
        public boolean equals(Object obj)
        {
            if(obj == this)
                return true;
            if(!(obj instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> other = (Map.Entry<?, ?>) obj;
            return Objects.equals(this.getKey(), other.getKey()) && Objects.equals(this.getValue(), other.getValue());
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(this.getKey(), this.getValue());
        }
    }
}
