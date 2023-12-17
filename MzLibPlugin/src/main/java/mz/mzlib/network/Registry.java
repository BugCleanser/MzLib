package mz.mzlib.network;

import mz.mzlib.util.IndexAllocator;

import java.util.Map;

public class Registry<T>
{
	public String name;
	public IndexAllocator<T> allocator;
	public Map<T,Integer> remap;
	public Registry(String name)
	{
		this.name=name;
	}
	public T get(int id)
	{
		return this.allocator.get(id);
	}
	public synchronized void add(int id,T object)
	{
		while(allocator.list.size()<=id)
			allocator.alloc();
		allocator.bin.remove(id);
		allocator.set(id,object);
		remap.put(object,id);
	}
	public synchronized int add(T object)
	{
		int i=allocator.alloc();
		allocator.set(i,object);
		remap.put(object,i);
		return i;
	}
	public synchronized void reg(int id,T object)
	{
		this.add(id,object);
	}
	public synchronized int reg(T object)
	{
		return this.add(object);
	}
	public synchronized void unreg(T object)
	{
		allocator.free(remap.remove(object));
	}
}
