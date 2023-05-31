package mz.lib.minecraft.bukkit.event;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.ProtocolUtil;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.wrappednms.NmsPacketPlayInSetCreativeSlot;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;

public class SetItemEvent extends Event implements Cancellable
{
	public @Deprecated
	static HandlerList handlers=new HandlerList();
	public Player player;
	public int slot;
	public ItemStackBuilder item;
	public @Deprecated
	boolean cancelled=false;
	
	public SetItemEvent(Player player,int slot,ItemStackBuilder item)
	{
		super(!Bukkit.getServer().isPrimaryThread());
		this.player=player;
		this.slot=slot;
		this.item=item;
	}
	
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance,ProtocolUtil.instance);
			reg(new ProtocolUtil.ReceiveListener<>(EventPriority.LOW,NmsPacketPlayInSetCreativeSlot.class,(player,packet,cancelled)->
			{
				if(cancelled.get())
					return;
				SetItemEvent e=new SetItemEvent(player,packet.getSlot(),new ItemStackBuilder(ObcItemStack.asCraftMirror(packet.getItem()).getRaw()));
				Bukkit.getPluginManager().callEvent(e);
				if(!e.isCancelled())
				{
					packet.setSlot(e.slot);
					packet.setItem(ObcItemStack.ensure(e.item.get()).getHandle());
				}
				else
					cancelled.set(true);
			}));
		}
	}
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
