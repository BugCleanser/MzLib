package mz.lib.minecraft.item;

import mz.lib.*;
import mz.lib.minecraft.*;
import mz.lib.minecraft.nbt.*;
import mz.mzlib.*;
import mz.mzlib.nbt.*;

public interface ItemStack
{
	static ItemStack newInstance(Item item,int amount)
	{
		return Factory.instance.newItemStack(item,amount);
	}
	static ItemStack newInstance(Item item)
	{
		return newInstance(item,1);
	}
	
	Item getItem();
	void setItem(Item item);
	
	int getDamage();
	void setDamage(int damage);
	
	NbtObject getTag();
	void setTag(NbtObject tag);
	default NbtObject tag()
	{
		if(TypeUtil.isNull(getTag()))
		{
			NbtObject r=NbtObject.newInstance();
			setTag(r);
			return r;
		}
		return getTag();
	}
}
