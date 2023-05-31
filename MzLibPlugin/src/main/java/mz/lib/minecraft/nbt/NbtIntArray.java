package mz.lib.minecraft.nbt;

import mz.lib.minecraft.*;
import mz.mzlib.*;

public interface NbtIntArray extends NbtList
{
	int[] getData();
	void setData(int[] data);
	
	static NbtIntArray newInstance()
	{
		return Factory.instance.newNbtIntArray();
	}
}
