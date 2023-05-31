package mz.lib.minecraft.nbt;

import mz.lib.minecraft.*;

public interface NbtPrimitive extends NbtElement
{
	static NbtPrimitive newInstance(Object value)
	{
		return Factory.instance.newNbtPrimitive(value);
	}
	
	Number getValue();
	
	default byte toByte()
	{
		return getValue().byteValue();
	}
	default short toShort()
	{
		return getValue().shortValue();
	}
	default int toInt()
	{
		return getValue().intValue();
	}
	default long toLong()
	{
		return getValue().longValue();
	}
	default float toFloat()
	{
		return getValue().floatValue();
	}
}
