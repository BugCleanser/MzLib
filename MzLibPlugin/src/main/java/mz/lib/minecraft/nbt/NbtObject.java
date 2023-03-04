package mz.lib.minecraft.nbt;

import mz.lib.minecraft.*;

import java.util.*;

public interface NbtObject extends NbtElement
{
	static NbtObject newInstance()
	{
		return Factory.instance.newNbtObject();
	}
	@SafeVarargs
	static NbtObject newInstance(Map.Entry<String,NbtElement> ...values)
	{
		NbtObject r=newInstance();
		for(Map.Entry<String,NbtElement> v:values)
			r.set(v.getKey(),v.getValue());
		return r;
	}
	
	boolean containsKey(String key);
	
	NbtElement get(String key);
	void set(String key,NbtElement value);
}
