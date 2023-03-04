package mz.lib.minecraft.nbt;

import mz.lib.minecraft.*;

public interface NbtObject extends NbtElement
{
	static NbtObject newInstance()
	{
		return Factory.instance.newNbtObject();
	}
	
	NbtElement get(String key);
	void set(String key,NbtElement value);
}
