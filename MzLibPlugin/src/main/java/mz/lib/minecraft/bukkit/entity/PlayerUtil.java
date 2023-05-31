package mz.lib.minecraft.bukkit.entity;

import mz.lib.minecraft.bukkit.wrappednms.*;
import mz.lib.minecraft.bukkit.wrappedobc.ObcEntity;
import mz.lib.minecraft.bukkit.wrappedobc.ObcInventory;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerUtil
{
	public static ItemStack getEquipment(HumanEntity player,EquipmentSlot slot)
	{
		switch(slot)
		{
			case CHEST:
				return player.getInventory().getChestplate();
			case FEET:
				return player.getInventory().getBoots();
			case HAND:
				return player.getInventory().getItemInMainHand();
			case HEAD:
				return player.getInventory().getHelmet();
			case LEGS:
				return player.getInventory().getLeggings();
			case OFF_HAND:
				return player.getInventory().getItemInOffHand();
			default:
				throw new IllegalArgumentException("slot "+slot);
		}
	}
	public static NmsContainer getPlayerContainer(Player player)
	{
		return WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityPlayer.class).getPlayerContainer();
	}
	public static NmsContainer getOpenContainer(Player player)
	{
		return WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityPlayer.class).getOpenContainer();
	}
	
	/**
	 * Give item to player
	 * @param player who you want to give to
	 * @param item what you want to give
	 * @return true if the player got all the item
	 */
	public static boolean give(Player player,ItemStack item)
	{
		item=ObcItemStack.asCraftCopy(item).getRaw();
		boolean picked=WrappedObject.wrap(ObcInventory.class,player.getInventory()).getNms().cast(NmsPlayerInventory.class).pickUp(ObcItemStack.ensure(item).getHandle());
		if(picked)
		{
			float f;
			if(BukkitWrapper.version>=19)
			{
				NmsRandomSourceV19 rand=WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityPlayer.class).getRandomV19();
				f=rand.nextFloat()-rand.nextFloat();
			}
			else
			{
				Random rand=WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityPlayer.class).getRandomV_19();
				f=rand.nextFloat()-rand.nextFloat();
			}
			player.getWorld().playSound(player.getLocation(),Sound.ENTITY_ITEM_PICKUP,0.2f,(f*0.7f+1.f)*2.f);
		}
		if(picked && ObcItemStack.isAir(item))
		{
			return true;
		}
		else
		{
			NmsEntityItem d=WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityPlayer.class).drop(ObcItemStack.ensure(item).getHandle(),false);
			if(!d.isNull())
			{
				Item drop=(Item)d.getBukkitEntity();
				drop.setPickupDelay(0);
			}
			return false;
		}
	}
}
