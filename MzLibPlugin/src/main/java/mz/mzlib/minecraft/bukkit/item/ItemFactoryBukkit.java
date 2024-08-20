package mz.mzlib.minecraft.bukkit.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemFactory;

public class ItemFactoryBukkit implements ItemFactory
{
    public static ItemFactoryBukkit instance = new ItemFactoryBukkit();

    @Override
    public Item get(Identifier id)
    {
        //TODO
        return null;
    }
}
