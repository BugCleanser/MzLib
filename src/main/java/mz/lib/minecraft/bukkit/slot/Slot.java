package mz.lib.minecraft.bukkit.slot;

import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import org.bukkit.inventory.ItemStack;

public interface Slot
{
	ItemStack getItem();
	void setItem(ItemStack item);
	default boolean isEmpty()
	{
		return ItemStackBuilder.isAir(getItem());
	}
}
