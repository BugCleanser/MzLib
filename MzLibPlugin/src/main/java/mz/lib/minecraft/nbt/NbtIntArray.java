package mz.lib.minecraft.nbt;

import mz.lib.minecraft.*;

import java.util.*;

public interface NbtIntArray extends NbtList
{
	int[] getData();
	void setData(int[] data);
	
	static NbtIntArray newInstance()
	{
		return Factory.instance.newNbtIntArray();
	}
	static NbtIntArray newInstance(int ...data)
	{
		NbtIntArray r=newInstance();
		r.setData(data);
		return r;
	}
	static NbtIntArray newInstance(UUID uuid)
	{
		return newInstance((int) (uuid.getMostSignificantBits()>>32),(int)uuid.getMostSignificantBits(),(int) (uuid.getLeastSignificantBits()>>32),(int)uuid.getLeastSignificantBits());
	}
}
