package mz.lib.minecraft.nbt;

import mz.lib.minecraft.*;

public interface NbtString extends NbtElement
{
	static NbtString newInstance(String value)
	{
		return Factory.instance.newNbtString(value);
	}
	
	String getValue();
}
