package mz.lib.minecraft.item;

import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkitlegacy.*;
import org.bukkit.*;

public interface MzBlockReservoirItem extends MzItem
{
	@Override
	default NamespacedKey getKey()
	{
		return new NamespacedKey(MzLib.instance,"mzblock_reservoir_item");
	}
	
	@Override
	default String getRawIdV_13()
	{
		return "minecraft:stick";
	}
	
	default void setData(NmsNBTTagList data)
	{
		mzTag().set("data",data);
	}
	default NmsNBTTagList getData()
	{
		return mzTag().getList("data");
	}
}
