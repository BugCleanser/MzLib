package mz.mzlib.javautil;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CopyOnWriteMap<K,V> extends AbstractMap<K,V>
{
	public Function<Map<K,V>,Map<K,V>> copier;
	public volatile Map<K,V> delegate;
	
	public CopyOnWriteMap(Function<Map<K,V>,Map<K,V>> copier,Map<K,V> delegate)
	{
		this.copier=copier;
		this.delegate=delegate;
	}
	
	public CopyOnWriteMap()
	{
		this(HashMap::new,new HashMap<>());
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
		return delegate.containsKey(key);
	}
	
	@Override
	public boolean containsValue(Object value)
	{
		return delegate.containsValue(value);
	}
	
	@Override
	public V get(Object key)
	{
		return delegate.get(key);
	}
	
	@Override
	public synchronized V put(K key,V value)
	{
		Map<K,V> next=copier.apply(delegate);
		V result=next.put(key,value);
		delegate=next;
		return result;
	}
	
	@Override
	public synchronized V remove(Object key)
	{
		return delegate.remove(key);
	}
	
	@Override
	public synchronized void putAll(Map<? extends K,? extends V> m)
	{
		Map<K,V> next=copier.apply(delegate);
		next.putAll(m);
		delegate=next;
	}
	
	@Override
	public synchronized void clear()
	{
		Map<K,V> next=copier.apply(delegate);
		next.clear();
		delegate=next;
	}
	
	@Override
	public Set<K> keySet()
	{
		return CollectionUtil.synchronizedSet(delegate.keySet(),this);
	}
	
	@Override
	public Collection<V> values()
	{
		return delegate.values();
	}
	
	@Override
	public Set<Entry<K,V>> entrySet()
	{
		return CollectionUtil.synchronizedSet(delegate.entrySet(),this);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof CopyOnWriteMap)
			return delegate.equals(((CopyOnWriteMap<?,?>)o).delegate);
		else
			return delegate.equals(o);
	}
	
	@Override
	public int hashCode()
	{
		return delegate.hashCode();
	}
	
	@Override
	public V getOrDefault(Object key,V defaultValue)
	{
		return delegate.getOrDefault(key,defaultValue);
	}
	
	@Override
	public void forEach(BiConsumer<? super K,? super V> action)
	{
		delegate.forEach(action);
	}
	
	@Override
	public synchronized void replaceAll(BiFunction<? super K,? super V,? extends V> function)
	{
		Map<K,V> next=copier.apply(delegate);
		next.replaceAll(function);
		delegate=next;
	}
	
	@Override
	public synchronized V putIfAbsent(K key,V value)
	{
		Map<K,V> next=copier.apply(delegate);
		V result=next.putIfAbsent(key,value);
		delegate=next;
		return result;
	}
	
	@Override
	public synchronized boolean remove(Object key,Object value)
	{
		Map<K,V> next=copier.apply(delegate);
		boolean result=next.remove(key,value);
		delegate=next;
		return result;
	}
	
	@Override
	public synchronized boolean replace(K key,V oldValue,V newValue)
	{
		Map<K,V> next=copier.apply(delegate);
		boolean result=next.replace(key,oldValue,newValue);
		delegate=next;
		return result;
	}
	
	@Override
	public synchronized V replace(K key,V value)
	{
		Map<K,V> next=copier.apply(delegate);
		V result=next.replace(key,value);
		delegate=next;
		return result;
	}
	
	@Override
	public synchronized V computeIfAbsent(K key,Function<? super K,? extends V> mappingFunction)
	{
		Map<K,V> next=copier.apply(delegate);
		V result=next.computeIfAbsent(key,mappingFunction);
		delegate=next;
		return result;
	}
	
	@Override
	public synchronized V computeIfPresent(K key,BiFunction<? super K,? super V,? extends V> remappingFunction)
	{
		Map<K,V> next=copier.apply(delegate);
		V result=next.computeIfPresent(key,remappingFunction);
		delegate=next;
		return result;
	}
	
	@Override
	public synchronized V compute(K key,BiFunction<? super K,? super V,? extends V> remappingFunction)
	{
		Map<K,V> next=copier.apply(delegate);
		V result=next.compute(key,remappingFunction);
		delegate=next;
		return result;
	}
	
	@Override
	public synchronized V merge(K key,V value,BiFunction<? super V,? super V,? extends V> remappingFunction)
	{
		Map<K,V> next=copier.apply(delegate);
		V result=next.merge(key,value,remappingFunction);
		delegate=next;
		return result;
	}
}
