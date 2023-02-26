package mz.lib.minecraft.bukkit.nms;

import com.google.common.collect.Lists;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.nbt.*;
import mz.mzlib.wrapper.*;

import java.util.List;
import java.util.UUID;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagIntArray",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagIntArray",minVer=17)})
public interface NmsNBTTagIntArray extends NmsNBTBase, NbtIntArray
{
	@WrappedConstructor
	NmsNBTTagIntArray staticNewInstance(List<Integer> list);
	static NmsNBTTagIntArray newInstance(List<Integer> list)
	{
		return WrappedObject.getStatic(NmsNBTTagIntArray.class).staticNewInstance(list);
	}
	static NmsNBTTagIntArray newInstance(Integer ...list)
	{
		return newInstance(Lists.newArrayList(list));
	}
	static NmsNBTTagIntArray newInstance(UUID uuid)
	{
		return newInstance((int) (uuid.getMostSignificantBits()>>32),(int)uuid.getMostSignificantBits(),(int) (uuid.getLeastSignificantBits()>>32),(int)uuid.getLeastSignificantBits());
	}
	default UUID asUUID()
	{
		return new UUID(((long)get0(0))<<32+get0(1),((long)get0(2))<<32+get0(3));
	}
	
	@Override
	default int size()
	{
		return getData().length;
	}
	
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	int[] getData();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	void setData(int[] data);
	
	default void set(int index,int value)
	{
		getData()[index]=value;
	}
	default void set(int index,NmsNBTTagInt value)
	{
		set(index,value.toInt());
	}
	
	@Override
	default void add(NbtElement element)
	{
		int[] n=new int[size()+1];
		System.arraycopy(getData(),0,n,0,size());
		setData(n);
		set(size()-1,element);
	}
	
	@Override
	default void set(int index,NbtElement value)
	{
		getData()[index]=((NbtPrimitive)value).toInt();
	}
	
	default int get0(int index)
	{
		return getData()[index];
	}
	default NmsNBTTagInt get(int index)
	{
		return NmsNBTTagInt.newInstance(get0(index));
	}
}
