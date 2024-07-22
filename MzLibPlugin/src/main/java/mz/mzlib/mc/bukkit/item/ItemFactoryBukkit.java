package mz.mzlib.mc.bukkit.item;

import mz.mzlib.mc.Identifier;
import mz.mzlib.mc.item.Item;
import mz.mzlib.mc.item.ItemFactory;

public class ItemFactoryBukkit implements ItemFactory
{
	public static ItemFactoryBukkit instance=new ItemFactoryBukkit();
	
	@Override
	public Item get(Identifier id)
	{
		//TODO
		return null;
	}
}
