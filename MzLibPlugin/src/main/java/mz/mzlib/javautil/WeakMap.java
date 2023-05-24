package mz.mzlib.javautil;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WeakMap<K,V> extends AbstractMap<K,V>
{
	public Map<WeakRef<K>,V> delegate;
	public WeakMap(Map<WeakRef<K>,V> delegate)
	{
		this.delegate=delegate;
	}
	public WeakMap()
	{
		this(new ConcurrentHashMap<>());
	}
	
	@Override
	public int size()
	{
		return delegate.size();
	}
	@Override
	public boolean isEmpty()
	{
		return delegate.isEmpty();
	}
	@Override
	public boolean containsKey(Object key)
	{
		return delegate.containsKey(new WeakRef<>(key));
	}
	@Override
	public boolean containsValue(Object value)
	{
		return delegate.containsValue(value);
	}
	@Override
	public V get(Object key)
	{
		return delegate.get(new WeakRef<>(key));
	}
	@Override
	public V put(K key,V value)
	{
		return delegate.put(new WeakRef<>(key),value);
	}
	@Override
	public V remove(Object key)
	{
		return delegate.remove(new WeakRef<>(key));
	}
	@Override
	public void putAll(Map<? extends K,? extends V> m)
	{
		delegate.putAll(MapUtil.newHashMap(m.entrySet().stream().map(e->new AbstractMap.SimpleEntry<>(new WeakRef<>((K)e.getKey()),e.getValue())).collect(Collectors.toSet())));
	}
	@Override
	public void clear()
	{
		delegate.clear();
	}
	@Override
	public Set<K> keySet()
	{
		return delegate.keySet().stream().map(WeakRef::get).collect(Collectors.toSet());
	}
	@Override
	public Collection<V> values()
	{
		return delegate.values();
	}
	@Override
	public Set<Entry<K,V>> entrySet()
	{
		return Collections.unmodifiableSet(delegate.entrySet().stream().map(e->new AbstractMap.SimpleEntry<>(e.getKey().get(),e.getValue())).collect(Collectors.toSet()));
	}
	@Override
	public boolean equals(Object o)
	{
		return o instanceof WeakMap&&Objects.equals(delegate,((WeakMap<?,?>)o).delegate) || super.equals(o);
	}
	@Override
	public int hashCode()
	{
		return delegate.hashCode();
	}
	@Override
	public V getOrDefault(Object key,V defaultValue)
	{
		return delegate.getOrDefault(new WeakRef<>(key),defaultValue);
	}
	@Override
	public void forEach(BiConsumer<? super K,? super V> action)
	{
		delegate.forEach((k,v)->action.accept(k.get(),v));
	}
	@Override
	public void replaceAll(BiFunction<? super K,? super V,? extends V> function)
	{
		delegate.replaceAll((k,v)->function.apply(k.get(),v));
	}
	@Override
	public V putIfAbsent(K key,V value)
	{
		return delegate.putIfAbsent(new WeakRef<>(key),value);
	}
	@Override
	public boolean remove(Object key,Object value)
	{
		return delegate.remove(new WeakRef<>(key),value);
	}
	@Override
	public boolean replace(K key,V oldValue,V newValue)
	{
		return delegate.replace(new WeakRef<>(key),oldValue,newValue);
	}
	@Override
	public V replace(K key,V value)
	{
		return delegate.replace(new WeakRef<>(key),value);
	}
	@SuppressWarnings("NullableProblems")
	@Override
	public V computeIfAbsent(K key,Function<? super K,? extends V> mappingFunction)
	{
		return delegate.computeIfAbsent(new WeakRef<>(key),k->mappingFunction.apply(k.get()));
	}
	@SuppressWarnings("NullableProblems")
	@Override
	public V computeIfPresent(K key,BiFunction<? super K,? super V,? extends V> remappingFunction)
	{
		return delegate.computeIfPresent(new WeakRef<>(key),(k,v)->remappingFunction.apply(k.get(),v));
	}
	@SuppressWarnings("NullableProblems")
	@Override
	public V compute(K key,BiFunction<? super K,? super V,? extends V> remappingFunction)
	{
		return delegate.compute(new WeakRef<>(key),(k,v)->remappingFunction.apply(k.get(),v));
	}
	@SuppressWarnings("NullableProblems")
	@Override
	public V merge(K key,V value,BiFunction<? super V,? super V,? extends V> remappingFunction)
	{
		return delegate.merge(new WeakRef<>(key),value,remappingFunction);
	}
}
