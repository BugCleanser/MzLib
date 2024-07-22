package mz.mzlib.mc.nbt;

import mz.mzlib.util.Instance;

public interface NBTCompound extends NBTElement
{
	NBTElement get(String name);
	void set(String name,NBTElement value);
	
	default NBTCompound getCompound(String name)
	{
		return (NBTCompound)this.get(name);
	}
	default byte getByte(String name)
	{
		return ((NBTByte)this.get(name)).getValue();
	}
	default short getShort(String name)
	{
		return ((NBTShort)this.get(name)).getValue();
	}
	default int getInt(String name)
	{
		return ((NBTInt)this.get(name)).getValue();
	}
	default long getLong(String name)
	{
		return ((NBTLong)this.get(name)).getValue();
	}
	default float getFloat(String name)
	{
		return ((NBTFloat)this.get(name)).getValue();
	}
	default double getDouble(String name)
	{
		return ((NBTDouble)this.get(name)).getValue();
	}
	default String getString(String name)
	{
		return ((NBTString)this.get(name)).getValue();
	}
	default byte[] getByteArray(String name)
	{
		return ((NBTByteArray)this.get(name)).getValue();
	}
	default int[] getIntArray(String name)
	{
		return ((NBTIntArray)this.get(name)).getValue();
	}
	default long[] getLongArray(String name)
	{
		return ((NBTLongArray)this.get(name)).getValue();
	}
	default void set(String name,byte value)
	{
		this.set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,short value)
	{
		this.set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,int value)
	{
		this.set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,long value)
	{
		this.set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,float value)
	{
		this.set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,double value)
	{
		this.set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,String value)
	{
		this.set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,byte[] value)
	{
		this.set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,int[] value)
	{
		this.set(name,Instance.get(NBTFactory.class).create(value));
	}
	default void set(String name,long[] value)
	{
		this.set(name,Instance.get(NBTFactory.class).create(value));
	}
}
