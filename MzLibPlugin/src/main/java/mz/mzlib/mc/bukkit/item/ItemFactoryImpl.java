package mz.mzlib.mc.bukkit.item;

import mz.mzlib.mc.Identifier;
import mz.mzlib.mc.item.Item;
import mz.mzlib.mc.item.ItemFactory;

public class ItemFactoryImpl implements ItemFactory
{
	public static ItemFactoryImpl instance=new ItemFactoryImpl();
	
	@Override
	public Item get(Identifier id)
	{
		//TODO
		return null;
	}
}
