package mz.lib.minecraft.event.entity.player;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.entity.PlayerUtil;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerUseItemEvent extends PlayerEvent
{
	public ItemStack item;
	public EquipmentSlot hand;
	public Block block;
	public BlockFace blockFace;
	public Entity entity;
	public PlayerUseItemEvent(Player user,EquipmentSlot hand)
	{
		super(user);
		this.item=PlayerUtil.getEquipment(user,hand);
		this.hand=hand;
	}
	
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance);
		}
		
		@EventHandler(priority=EventPriority.LOW)
		public void onPlayerInteract(PlayerInteractEvent event)
		{
			switch(event.getAction())
			{
				case RIGHT_CLICK_AIR:
				case RIGHT_CLICK_BLOCK:
					PlayerUseItemEvent e=new PlayerUseItemEvent(event.getPlayer(),event.getHand());
					if(event.hasBlock())
					{
						e.block=event.getClickedBlock();
						e.blockFace=event.getBlockFace();
					}
					Bukkit.getPluginManager().callEvent(e);
					if(e.isCancelled())
						event.setCancelled(true);
					break;
			}
		}
		
		@EventHandler(ignoreCancelled=true)
		public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
		{
			PlayerUseItemEvent e=new PlayerUseItemEvent(event.getPlayer(),event.getHand());
			e.entity=event.getRightClicked();
			Bukkit.getPluginManager().callEvent(e);
			event.setCancelled(e.isCancelled());
		}
	}
	
	public static HandlerList handlers=new HandlerList();
	public boolean cancelled=false;
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public HandlerList getHandlers()
	{
		return getHandlerList();
	}
	public boolean isCancelled()
	{
		return cancelled;
	}
	public void setCancelled(boolean cancelled)
	{
		this.cancelled=cancelled;
	}
}
