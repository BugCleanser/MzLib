package mz.mzlib.network;

import mz.mzlib.util.IndexAllocator;

import java.util.Map;

public class Registry<T>
{
	public Protocol protocol;
	public IndexAllocator<T> allocator;
	public Map<T,Integer> remap;
	
	public Registry(Protocol protocol)
	{
		this.protocol=protocol;
	}
	
	public synchronized void reg(int id,T object)
	{
		while(allocator.list.size()<=id)
			allocator.alloc();
		allocator.bin.remove(id);
		allocator.set(id,object);
		remap.put(object,id);
	}
	public synchronized int reg(T object)
	{
		int i=allocator.alloc();
		allocator.set(i,object);
		remap.put(object,i);
		return i;
	}
	public synchronized void unreg(T object)
	{
		allocator.free(remap.remove(object));
	}
}
