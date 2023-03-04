package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagByteArray",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagByteArray",minVer=17)})
public interface NmsNBTTagByteArray extends NmsNBTBase, NbtByteArray
{
	static NmsNBTTagByteArray newInstance()
	{
		return WrappedObject.getStatic(NmsNBTTagByteArray.class).staticNewInstance(new byte[0]);
	}
	@VersionalWrappedConstructor
	NmsNBTTagByteArray staticNewInstance(byte[] data);
	
	@VersionalWrappedFieldAccessor(@VersionalName({"data","@0"}))
	byte[] getData();
	@VersionalWrappedFieldAccessor(@VersionalName({"data","@0"}))
	void setData(byte[] data);
	
	@Override
	default int size()
	{
		return getData().length;
	}
	
	@Override
	default void add(NbtElement element)
	{
		byte[] n=new byte[size()+1];
		System.arraycopy(getData(),0,n,0,size());
		setData(n);
		set(size()-1,element);
	}
	
	@Override
	default NbtElement get(int index)
	{
		return NmsNBTTagByte.newInstance(getData()[index]);
	}
	
	@Override
	default void set(int index,NbtElement value)
	{
		getData()[index]=((NbtPrimitive)value).toByte();
	}
}
