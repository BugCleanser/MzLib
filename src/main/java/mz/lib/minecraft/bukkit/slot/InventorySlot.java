package mz.lib.minecraft.bukkit.slot;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventorySlot implements Slot
{
	public @Deprecated
	Inventory inv;
	public @Deprecated
	int index;
	public InventorySlot(Inventory inv,int index)
	{
		this.inv=inv;
		this.index=index;
	}
	public InventorySlot(InventoryView view,int rawSlot)
	{
		this.index=view.convertSlot(rawSlot);
		this.inv=this.index==rawSlot?view.getTopInventory():view.getBottomInventory();
	}
	public Inventory getInventory()
	{
		return inv;
	}
	public int getIndex()
	{
		return index;
	}
	@Override
	public ItemStack getItem()
	{
		return inv.getItem(index);
	}
	@Override
	public void setItem(ItemStack item)
	{
		inv.setItem(index,item);
	}
}
