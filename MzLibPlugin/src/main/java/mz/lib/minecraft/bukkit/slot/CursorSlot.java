package mz.lib.minecraft.bukkit.slot;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

public class CursorSlot implements Slot
{
	public @Deprecated
	HumanEntity player;
	public CursorSlot(HumanEntity player)
	{
		this.player=player;
	}
	public HumanEntity getPlayer()
	{
		return player;
	}
	@Override
	public ItemStack getItem()
	{
		return player.getItemOnCursor();
	}
	@Override
	public void setItem(ItemStack item)
	{
		player.setItemOnCursor(item);
	}
}
