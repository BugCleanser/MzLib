package mz.lib.minecraft.bukkitlegacy.command.argparser;

import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.nms.NmsNBTTagCompound;
import org.bukkit.command.CommandSender;

public class ItemStackBuilderArgParser extends AbsArgParser<ItemStackBuilder>
{
	public ItemStackBuilderArgParser()
	{
		super(ItemStackBuilder.class);
	}
	
	@Override
	public String getTypeName(CommandSender player,double max,double min)
	{
		return "nbt";
	}
	@Override
	public ItemStackBuilder parse(CommandSender sender,String json) throws Throwable
	{
		return new ItemStackBuilder(NmsNBTTagCompound.newInstance(json));
	}
	public boolean checkFront(CommandSender sender,String arg,double min,double max)
	{
		return true;
	}
}
