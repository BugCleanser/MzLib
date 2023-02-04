package mz.lib.minecraft.bukkit.slot;

import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

public class ItemFrameSlot implements Slot
{
	public ItemFrame entity;
	
	public ItemFrameSlot(ItemFrame entity)
	{
		this.entity=entity;
	}
	
	@Override
	public ItemStack getItem()
	{
		return entity.getItem();
	}
	@Override
	public void setItem(ItemStack item)
	{
		entity.setItem(item);
	}
}
