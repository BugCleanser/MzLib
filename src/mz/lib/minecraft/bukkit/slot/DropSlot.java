package mz.lib.minecraft.bukkit.slot;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class DropSlot implements Slot
{
	public Item entity;
	public DropSlot(Item entity)
	{
		this.entity=entity;
	}
	
	@Override
	public ItemStack getItem()
	{
		return entity.getItemStack();
	}
	
	@Override
	public void setItem(ItemStack item)
	{
		entity.setItemStack(item);
	}
}
