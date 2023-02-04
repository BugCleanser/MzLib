package mz.lib.minecraft.bukkit.slot;

import mz.lib.minecraft.bukkit.wrappednms.NmsEntityHuman;
import mz.lib.minecraft.bukkit.wrappedobc.ObcEntity;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

public class PlayerDropSlot implements Slot
{
	public @Deprecated
	HumanEntity player;
	
	public PlayerDropSlot(HumanEntity player)
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
		return null;
	}
	@Override
	public void setItem(ItemStack item)
	{
		WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityHuman.class).drop(ObcItemStack.asNMSCopy(item),true);
	}
}
