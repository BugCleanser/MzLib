package mz.lib;

import java.util.*;

public class IncrementalIdMap<T> implements Map<Integer,T>
{
	public List<T> store;
	public Set<Integer> unused;
	
	public IncrementalIdMap()
	{
		this(new ArrayList<>(),new HashSet<>());
	}
	public IncrementalIdMap(List<T> store,Set<Integer> unused)
	{
		this.store=store;
		this.unused=unused;
	}
	
	public int alloc()
	{
		if(!unused.isEmpty())
		{
			Iterator<Integer> it=unused.iterator();
			Integer r=it.next();
			it.remove();
			return r;
		}
		store.add(null);
		return store.size()-1;
	}
	public int alloc(T value)
	{
		int id=alloc();
		store.set(id,value);
		return id;
	}
	
	@Override
	public int size()
	{
		return store.size()-unused.size();
	}
	
	@Override
	public boolean isEmpty()
	{
		return size()==0;
	}
	
	@Override
	public boolean containsKey(Object key)
	{
		if(key instanceof Integer)
			return ((Integer)key)<store.size()&&!unused.contains(key);
		return false;
	}
	
	@Override
	public boolean containsValue(Object value)
	{
		return store.contains(value);
	}
	
	@Override
	public T get(Object key)
	{
		return store.get((Integer) key);
	}
	
	@Nullable
	@Override
	public T put(Integer key,T value)
	{
		if(store.size()>key)
		{
			unused.remove(key);
			return store.set(key,value);
		}
		while(store.size()<key)
		{
			unused.add(store.size());
			store.add(null);
		}
		store.add(value);
		return null;
	}
	
	@Override
	public T remove(Object key)
	{
		unused.add((Integer) key);
		T r=store.get((Integer) key);
		optimize();
		return r;
	}
	
	@Override
	public void putAll(@NotNull Map<? extends Integer,? extends T> m)
	{
		m.forEach((k,v)->put(k,v));
	}
	
	@Override
	public void clear()
	{
		store.clear();
		unused.clear();
	}
	
	@NotNull
	@Override
	public Set<Integer> keySet()
	{
		return new AbstractSet<Integer>()
		{
			@Override
			public Iterator<Integer> iterator()
			{
				return new Iterator<Integer>()
				{
					int next=0;
					@Override
					public boolean hasNext()
					{
						return store.size()>next;
					}
					@Override
					public Integer next()
					{
						while(unused.contains(next))
							next++;
						return next++;
					}
					@Override
					public void remove()
					{
						unused.add(next-1);
						optimize();
					}
				};
			}
			@Override
			public int size()
			{
				return IncrementalIdMap.this.size();
			}
		};
	}
	
	public void optimize()
	{
		while(unused.contains(store.size()-1))
		{
			store.remove(store.size()-1);
			unused.remove(store.size());
		}
	}
	
	@NotNull
	@Override
	public Collection<T> values()
	{
		ArrayList<T> r=new ArrayList<>(size());
		for(int i=0;i<store.size();i++)
		{
			if(unused.contains(i))
				r.add(store.get(i));
		}
		return r;
	}
	
	@NotNull
	@Override
	public Set<Entry<Integer,T>> entrySet()
	{
		return new AbstractSet<Entry<Integer,T>>()
		{
			@Override
			public Iterator<Entry<Integer,T>> iterator()
			{
				return new Iterator<Entry<Integer,T>>()
				{
					int next=0;
					
					@Override
					public boolean hasNext()
					{
						while(unused.contains(next))
							next++;
						return next<store.size();
					}
					
					@Override
					public Entry<Integer,T> next()
					{
						int r=next++;
						return new Entry<Integer,T>()
						{
							@Override
							public Integer getKey()
							{
								return r;
							}
							@Override
							public T getValue()
							{
								return store.get(r);
							}
							@Override
							public T setValue(T value)
							{
								return store.set(r,value);
							}
						};
					}
					
					@Override
					public void remove()
					{
						unused.add(next-1);
						optimize();
					}
				};
			}
			
			@Override
			public int size()
			{
				return IncrementalIdMap.this.size();
			}
		};
	}
}
