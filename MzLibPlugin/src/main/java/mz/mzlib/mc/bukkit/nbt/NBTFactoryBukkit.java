package mz.mzlib.mc.bukkit.nbt;

import mz.mzlib.mc.nbt.*;

public class NBTFactoryBukkit implements NBTFactory
{
	@Override
	public NBTCompoundBukkit createCompound()
	{
		return NBTCompoundBukkit.newInstance();
	}
	
	//TODO
	@Override
	public NBTByte create(byte value)
	{
		return null;
	}
	
	@Override
	public NBTShort create(short value)
	{
		return null;
	}
	
	@Override
	public NBTInt create(int value)
	{
		return null;
	}
	
	@Override
	public NBTLong create(long value)
	{
		return null;
	}
	
	@Override
	public NBTFloat create(float value)
	{
		return null;
	}
	
	@Override
	public NBTDouble create(double value)
	{
		return null;
	}
	
	@Override
	public NBTString create(String value)
	{
		return null;
	}
	
	@Override
	public NBTByteArray create(byte[] value)
	{
		return null;
	}
	
	@Override
	public NBTIntArray create(int[] value)
	{
		return null;
	}
	
	@Override
	public NBTLongArray create(long[] value)
	{
		return null;
	}
	
	@Override
	public NBTList create(NBTElement... value)
	{
		return null;
	}
}
