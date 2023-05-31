package mz.lib.minecraft.item;

import mz.lib.minecraft.*;

public interface Item
{
	static Item fromId(Identifier id)
	{
		return Factory.instance.getItem(id);
	}
	
	Identifier getId();
}
