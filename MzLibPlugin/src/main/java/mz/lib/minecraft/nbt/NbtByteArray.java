package mz.lib.minecraft.nbt;

import mz.lib.minecraft.*;
import mz.mzlib.*;

public interface NbtByteArray extends NbtList
{
	byte[] getData();
	void setData(byte[] data);
	
	static NbtByteArray newInstance()
	{
		return Factory.instance.newNbtByteArray();
	}
}
