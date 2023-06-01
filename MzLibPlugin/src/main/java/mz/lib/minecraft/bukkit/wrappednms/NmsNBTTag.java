package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.wrapper.WrappedObject;

import java.util.List;

public interface NmsNBTTag extends NmsNBTBase
{
	static NmsNBTTag wrap(Object value)
	{
		if(value==null)
			return null;
		if(value.getClass()==WrappedObject.getRawClass(NmsNBTTagByte.class))
			return WrappedObject.wrap(NmsNBTTagByte.class,value);
		if(value.getClass()==WrappedObject.getRawClass(NmsNBTTagCompound.class))
			return WrappedObject.wrap(NmsNBTTagCompound.class,value);
		if(value.getClass()==WrappedObject.getRawClass(NmsNBTTagInt.class))
			return WrappedObject.wrap(NmsNBTTagInt.class,value);
		if(value.getClass()==WrappedObject.getRawClass(NmsNBTTagList.class))
			return WrappedObject.wrap(NmsNBTTagList.class,value);
		if(value.getClass()==WrappedObject.getRawClass(NmsNBTTagLong.class))
			return WrappedObject.wrap(NmsNBTTagLong.class,value);
		if(value.getClass()==WrappedObject.getRawClass(NmsNBTTagShort.class))
			return WrappedObject.wrap(NmsNBTTagShort.class,value);
		if(value.getClass()==WrappedObject.getRawClass(NmsNBTTagString.class))
			return WrappedObject.wrap(NmsNBTTagString.class,value);
		if(value.getClass()==WrappedObject.getRawClass(NmsNBTTagFloat.class))
			return WrappedObject.wrap(NmsNBTTagFloat.class,value);
		if(value.getClass()==WrappedObject.getRawClass(NmsNBTTagDouble.class))
			return WrappedObject.wrap(NmsNBTTagDouble.class,value);
		throw new IllegalArgumentException("the value must be a nms NBTTag");
	}
	static NmsNBTTag wrapValue(Object value)
	{
		if(value==null)
			return null;
		if(value instanceof NmsNBTTagCompound)
			return (NmsNBTTag) value;
		if(value instanceof Byte)
			return NmsNBTTagByte.newInstance((Byte) value);
		if(value instanceof Integer)
			return NmsNBTTagInt.newInstance((Integer) value);
		if(value instanceof List<?>)
			return NmsNBTTagList.wrapValues((List<?>) value);
		if(value instanceof Long)
			return NmsNBTTagLong.newInstance((Long) value);
		if(value instanceof Short)
			return NmsNBTTagShort.newInstance((Short) value);
		if(value instanceof String)
			return NmsNBTTagString.newInstance((String) value);
		if(value instanceof Float)
			return NmsNBTTagFloat.newInstance((Float) value);
		if(value instanceof Double)
			return NmsNBTTagDouble.newInstance((Double) value);
		throw new IllegalArgumentException("the value can't be wrapped");
	}
}
