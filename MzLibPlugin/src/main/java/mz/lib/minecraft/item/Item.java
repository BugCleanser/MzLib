package mz.lib.minecraft.item;

import mz.lib.minecraft.*;

public interface Item
{
	static Item fromId(Identifier id)
	{
		return Factory.instance.getItem(id);
	}
	static Item fromId(String id)
	{
		return fromId(Identifier.newInstance(id));
	}
	
	Identifier getId();
	
	Item AIR=fromId("air");
	Item CLOCK=fromId("clock");
	Item CRAFTING_TABLE=fromId("crafting_table");
	Item ENCHANTED_BOOK=fromId("enchanted_book");
	Item ENDER_EYE=fromId("ender_eye");
	Item WRITTEN_BOOK=fromId("written_book");
}
