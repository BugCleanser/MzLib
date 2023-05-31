package mz.lib.minecraft.bukkit.wrappednms;

import com.google.common.collect.Lists;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

import java.util.AbstractList;
import java.util.List;
import java.util.UUID;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagIntArray",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagIntArray",minVer=17)})
public interface NmsNBTTagIntArray extends NmsNBTTag
{
	@Override
	AbstractList<Object> getRaw();
	
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
		return new UUID(((long)get(0))<<32+get(1),((long)get(2))<<32+get(3));
	}
	
	default void set(int index,NmsNBTTagInt value)
	{
		getRaw().set(index,value.getRaw());
	}
	default void set(int index,int value)
	{
		set(index,NmsNBTTagInt.newInstance(value));
	}
	
	default NmsNBTTagInt getRaw(int index)
	{
		return WrappedObject.wrap(NmsNBTTagInt.class,getRaw().get(index));
	}
	default int get(int index)
	{
		return getRaw(index).getValue();
	}
}
