package mz.mzlib.mc.nbt;

import mz.mzlib.util.Instance;

public interface NBTCompound extends NBTElement
{
	NBTElement get(String name);
	NBTElement set(String name,NBTElement value);
	
	default NBTCompound getCompound(String name)
	{
		return (NBTCompound)get(name);
	}
	default byte getByte(String name)
	{
		return ((NBTByte)get(name)).getValue();
	}
	default short getShort(String name)
	{
		return ((NBTShort)get(name)).getValue();
	}
	default int getInt(String name)
	{
		return ((NBTInt)get(name)).getValue();
	}
	default long getLong(String name)
	{
		return ((NBTLong)get(name)).getValue();
	}
	default float getFloat(String name)
	{
		return ((NBTFloat)get(name)).getValue();
	}
	default double getDouble(String name)
	{
		return ((NBTDouble)get(name)).getValue();
	}
	default String getString(String name)
	{
		return ((NBTString)get(name)).getValue();
	}
	default byte[] getByteArray(String name)
	{
		return ((NBTByteArray)get(name)).getValue();
	}
	default int[] getIntArray(String name)
	{
		return ((NBTIntArray)get(name)).getValue();
	}
	default long[] getLongArray(String name)
	{
		return ((NBTLongArray)get(name)).getValue();
	}
	default void set(String name,byte value)
	{
		set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,short value)
	{
		set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,int value)
	{
		set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,long value)
	{
		set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,float value)
	{
		set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,double value)
	{
		set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,String value)
	{
		set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,byte[] value)
	{
		set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,int[] value)
	{
		set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,long[] value)
	{
		set(name,Instance.get(NBTFactory.class).create(value));
	}
}
