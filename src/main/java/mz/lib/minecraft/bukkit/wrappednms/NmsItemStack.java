package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.ItemStack",maxVer=17),@VersionName(value="net.minecraft.world.item.ItemStack",minVer=17)})
public interface NmsItemStack extends WrappedBukkitObject
{
	@WrappedBukkitConstructor(maxVer=13)
	NmsItemStack staticNewInstanceV_13(NmsItem item,int count);
	@WrappedBukkitConstructor(minVer=13)
	NmsItemStack staticNewInstanceV13(NmsIMaterialV13 item,int count);
	@WrappedBukkitMethod(@VersionName(value={"fromCompound","a"},minVer=13))
	NmsItemStack staticFromNbtV13(NmsNBTTagCompound nbt);
	@WrappedBukkitConstructor(maxVer=13)
	NmsItemStack staticNewInstanceV_13(NmsNBTTagCompound nbt);
	static NmsItemStack fromNbtV13(NmsNBTTagCompound nbt)
	{
		return WrappedObject.getStatic(NmsItemStack.class).staticFromNbtV13(nbt);
	}
	static NmsItemStack newInstanceV_13(NmsNBTTagCompound nbt)
	{
		return WrappedObject.getStatic(NmsItemStack.class).staticNewInstanceV_13(nbt);
	}
	static NmsItemStack fromNbt(NmsNBTTagCompound nbt)
	{
		if(BukkitWrapper.v13)
			return fromNbtV13(nbt);
		else
			return newInstanceV_13(nbt);
	}
	static NmsItemStack newInstance(NmsItem item,int count)
	{
		if(BukkitWrapper.v13)
			return WrappedObject.getStatic(NmsItemStack.class).staticNewInstanceV13(item.cast(NmsIMaterialV13.class),count);
		else
			return WrappedObject.getStatic(NmsItemStack.class).staticNewInstanceV_13(item,count);
	}
	static NmsItemStack newInstance(NmsItem item)
	{
		return newInstance(item,1);
	}
	
	@WrappedMethod({"save","b"})
	NmsNBTTagCompound save(NmsNBTTagCompound nbt);
	
	@WrappedBukkitFieldAccessor({@VersionName("item"),@VersionName(value="@0",minVer=17)})
	NmsItem getItem();
	@WrappedBukkitFieldAccessor({@VersionName("item"),@VersionName(value="@0",minVer=17)})
	NmsItemStack setItem(NmsItem item);
	
	@WrappedBukkitFieldAccessor({@VersionName("tag"),@VersionName(value="@0",minVer=17)})
	NmsNBTTagCompound getTag();
	@WrappedBukkitFieldAccessor({@VersionName("tag"),@VersionName(value="@0",minVer=17)})
	NmsItemStack setTag(NmsNBTTagCompound tag);
	
	default String getTranslateKey()
	{
		if(getItem().isNull())
			return NmsItem.fromId("air").getTranslateKey(this);
		return getItem().getTranslateKey(this);
	}
	
	@WrappedBukkitMethod({@VersionName("cloneItemStack"),@VersionName(value="m",minVer=18,maxVer=18.2f),@VersionName(value="n",minVer=18.2f,maxVer=19),@VersionName(value="o",minVer=19)})
	NmsItemStack cloneItemStack();
}
