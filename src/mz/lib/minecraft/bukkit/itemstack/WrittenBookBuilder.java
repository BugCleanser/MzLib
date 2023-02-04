package mz.lib.minecraft.bukkit.itemstack;

import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagCompound;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagString;
import org.bukkit.inventory.ItemStack;

public class WrittenBookBuilder extends ItemStackBuilder
{
	public static final String id="minecraft:written_book";
	public WrittenBookBuilder()
	{
		super(id);
	}
	public WrittenBookBuilder(ItemStackBuilder is)
	{
		super(is);
	}
	public WrittenBookBuilder(NmsNBTTagCompound nbt)
	{
		super(nbt);
	}
	public WrittenBookBuilder(ItemStack is)
	{
		super(is);
	}
	
	public boolean hasTitle()
	{
		return hasTag()&&tag().containsKey("title");
	}
	public String getTitle()
	{
		return tag().getString("title");
	}
	public WrittenBookBuilder setTitle(String title)
	{
		tag().set("title",NmsNBTTagString.newInstance(title));
		return this;
	}
}
