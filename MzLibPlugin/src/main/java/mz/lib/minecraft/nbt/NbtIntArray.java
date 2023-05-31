package mz.lib.minecraft.nbt;

import mz.lib.minecraft.*;

public interface NbtIntArray extends NbtList
{
	int[] getData();
	void setData(int[] data);
	
	static NbtIntArray newInstance()
	{
		return Factory.instance.newNbtIntArray();
	}
}
