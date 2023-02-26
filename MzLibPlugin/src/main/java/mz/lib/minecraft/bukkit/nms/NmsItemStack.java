package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.item.*;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.*;
import mz.mzlib.item.*;
import mz.mzlib.nbt.*;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.ItemStack",maxVer=17),@VersionalName(value="net.minecraft.world.item.ItemStack",minVer=17)})
public interface NmsItemStack extends VersionalWrappedObject, ItemStack
{
	@VersionalWrappedConstructor(maxVer=13)
	NmsItemStack staticNewInstanceV_13(NmsItem item,int count);
	@VersionalWrappedConstructor(minVer=13)
	NmsItemStack staticNewInstanceV13(NmsIMaterialV13 item,int count);
	@VersionalWrappedMethod(@VersionalName(value={"fromCompound","a"},minVer=13))
	NmsItemStack staticFromNbtV13(NmsNBTTagCompound nbt);
	@VersionalWrappedConstructor(maxVer=13)
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
		if(Server.instance.v13)
			return fromNbtV13(nbt);
		else
			return newInstanceV_13(nbt);
	}
	static NmsItemStack newInstance(NmsItem item,int count)
	{
		if(Server.instance.v13)
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
	
	@VersionalWrappedFieldAccessor({@VersionalName("item"),@VersionalName(value="@0",minVer=17)})
	NmsItem getItem();
	@VersionalWrappedFieldAccessor({@VersionalName("item"),@VersionalName(value="@0",minVer=17)})
	NmsItemStack setItem(NmsItem item);
	
	@VersionalWrappedFieldAccessor(@VersionalName(value="damage",maxVer=13))
	int getDamageV_13();
	@VersionalWrappedFieldAccessor(@VersionalName(value="damage",maxVer=13))
	void setDamageV_13(int damage);
	@Override
	default int getDamage()
	{
		if(Server.instance.v13)
		{
			Object r=tag().get("Damage");
			return WrappedObject.wrap(NmsNBTTagInt.class,r).getValue0();
		}
		else
			return getDamageV_13();
	}
	@Override
	default void setDamage(int damage)
	{
		if(Server.instance.v13)
			tag().set("Damage",NbtPrimitive.newInstance(damage));
		else
			setDamageV_13(damage);
	}
	
	@VersionalWrappedFieldAccessor({@VersionalName("tag"),@VersionalName(value="@0",minVer=17)})
	NmsNBTTagCompound getTag();
	@VersionalWrappedFieldAccessor({@VersionalName("tag"),@VersionalName(value="@0",minVer=17)})
	NmsItemStack setTag(NmsNBTTagCompound tag);
	default void setTag(NbtObject tag)
	{
		setTag((NmsNBTTagCompound) tag);
	}
	
	default String getTranslateKey()
	{
		if(getItem().isNull())
			return NmsItem.fromStringId("air").getTranslateKey(this);
		return getItem().getTranslateKey(this);
	}
	
	@VersionalWrappedMethod({@VersionalName("cloneItemStack"),@VersionalName(value="m",minVer=18,maxVer=18.2f),@VersionalName(value="n",minVer=18.2f,maxVer=19),@VersionalName(value="o",minVer=19)})
	NmsItemStack cloneItemStack();
}
