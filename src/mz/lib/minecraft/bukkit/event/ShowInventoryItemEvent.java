package mz.lib.minecraft.bukkit.event;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.ProtocolUtil;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.wrappednms.NmsItemStack;
import mz.lib.minecraft.bukkit.wrappednms.NmsPacketPlayOutSetSlot;
import mz.lib.minecraft.bukkit.wrappednms.NmsPacketPlayOutWindowItems;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShowInventoryItemEvent extends ShowItemEvent
{
	public int slot;
	public ShowInventoryItemEvent(Player player,int slot,ItemStack item)
	{
		super(player,item);
		this.slot=slot;
	}
	
	private static final HandlerList handlers=new HandlerList();
	public boolean cancelled=false;
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public boolean callParent=false;
	public HandlerList getHandlers()
	{
		if(callParent)
			return super.getHandlers();
		return handlers;
	}
	public boolean isCancelled()
	{
		return cancelled;
	}
	public void setCancelled(boolean cancelled)
	{
		this.cancelled=cancelled;
	}
	
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance,ProtocolUtil.instance);
		}
		
		@Override
		public void onEnable()
		{
			reg(new ProtocolUtil.SendListener<>(EventPriority.HIGHEST,NmsPacketPlayOutSetSlot.class,(player,packet,cancelled)->
			{
				if(cancelled.get())
					return;
				ShowInventoryItemEvent e=new ShowInventoryItemEvent(player,packet.getSlot(),ObcItemStack.asCraftMirror(packet.getItem()).getRaw());
				Bukkit.getPluginManager().callEvent(e);
				e.callParent=true;
				Bukkit.getPluginManager().callEvent(e);
				packet.setSlot(e.slot);
				packet.setItem(e.item.getHandle());
			}));
			reg(new ProtocolUtil.SendListener<>(EventPriority.HIGHEST,NmsPacketPlayOutWindowItems.class,(player,packet,cancelled)->
			{
				if(cancelled.get())
					return;
				List<Object> list=packet.getItems();
				for(int i=0;i<list.size();i++)
				{
					ShowInventoryItemEvent e=new ShowInventoryItemEvent(player,i,ObcItemStack.asCraftMirror(WrappedObject.wrap(NmsItemStack.class,list.get(i))).getRaw());
					Bukkit.getPluginManager().callEvent(e);
					e.callParent=true;
					Bukkit.getPluginManager().callEvent(e);
					list.set(e.slot,e.item.getHandle().getRaw());
				}
				if(BukkitWrapper.v17)
				{
					ShowItemEvent e=new ShowItemEvent(player,ObcItemStack.asCraftMirror(packet.getCursorV17()).getRaw());
					Bukkit.getPluginManager().callEvent(e);
					packet.setCursorV17(e.item.getHandle());
				}
			}));
		}
	}
}
