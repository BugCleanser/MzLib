package mz.lib.minecraft.nbt;

import mz.lib.minecraft.*;

import java.util.*;

public interface NbtList extends NbtElement
{
	static NbtList newInstance()
	{
		return Factory.instance.newNbtList();
	}
	static <T extends NbtElement> NbtList newInstance(List<T> l)
	{
		NbtList r=newInstance();
		l.forEach(r::add);
		return r;
	}
	static <T extends NbtElement> NbtList newInstance(T ...l)
	{
		NbtList r=newInstance();
		for(T i:l)
			r.add(i);
		return r;
	}
	
	int size();
	void add(NbtElement element);
	NbtElement get(int index);
	void set(int index,NbtElement value);
}
