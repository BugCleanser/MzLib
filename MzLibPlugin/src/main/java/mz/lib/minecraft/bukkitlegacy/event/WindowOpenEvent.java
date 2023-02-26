package mz.lib.minecraft.bukkitlegacy.event;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.bukkitlegacy.ProtocolUtil;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.bukkit.nms.NmsIChatBaseComponent;
import mz.lib.minecraft.bukkit.nms.NmsPacketPlayOutOpenWindow;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class WindowOpenEvent extends PlayerEvent implements Cancellable
{
	public Player player;
	public NmsIChatBaseComponent title;
	public WindowOpenEvent(Player player,NmsIChatBaseComponent title)
	{
		super(player);
		WrappedObject.wrap(WrappedEvent.class,this).setAsync(!Bukkit.getServer().isPrimaryThread());
		this.player=player;
		this.title=title;
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
			reg(new ProtocolUtil.SendListener<>(EventPriority.LOW,NmsPacketPlayOutOpenWindow.class,(pl,pa,c)->
			{
				WindowOpenEvent event=new WindowOpenEvent(pl,pa.getTitle());
				event.cancelled=c.get();
				Bukkit.getPluginManager().callEvent(event);
				c.set(event.isCancelled());
				pa.setTitle(event.title);
			}));
		}
	}
	
	private static final HandlerList handlers=new HandlerList();
	public boolean cancelled=false;
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public HandlerList getHandlers()
	{
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
}
