package mz.lib.minecraft.bukkit.event;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.ProtocolUtil;
import mz.lib.minecraft.bukkit.message.MessageComponent;
import mz.lib.minecraft.bukkit.message.WrappedComponentPaper;
import mz.lib.minecraft.bukkit.message.WrappedPaperAdventure;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.paper.*;
import mz.lib.minecraft.bukkit.wrappednms.NmsPacketPlayOutChat;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.wrapper.WrappedObject;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;

import java.util.Locale;

public class PlayerReceiveMsgEvent extends Event implements Cancellable
{
	public Player player;
	public MessageComponent msg;
	public PlayerReceiveMsgEvent(Player player,MessageComponent msg)
	{
		super(!Bukkit.getServer().isPrimaryThread());
		this.player=player;
		this.msg=msg;
	}
	
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance,ProtocolUtil.instance);
			reg(new ProtocolUtil.SendListener<>(EventPriority.LOW,NmsPacketPlayOutChat.class,(player,packet,c)->
			{
				if(c.get())
					return;
				Object component=null;
				if(BukkitWrapper.v17&&PaperModule.instance.isPaper())
				{
					component=WrappedObject.getRaw(packet.getComponentPaperV17());
					packet.setComponentPaperV17(WrappedObject.wrap(WrappedComponentPaperV17.class,null));
				}
				BaseComponent[] md5=null;
				if(BukkitWrapper.version<19)
					md5=packet.getMd5MsgV_19();
				MessageComponent msg;
				if(md5!=null)
				{
					msg=MessageComponent.parse(md5);
					packet.setMd5MsgV_19(null);
				}
				else if(component!=null)
					msg=MessageComponent.parse(WrappedPaperAdventure.asJsonString(WrappedObject.wrap(WrappedComponentPaper.class,component),Locale.forLanguageTag(player.getLocale())));
				else if(BukkitWrapper.version<19)
					msg=MessageComponent.parse(packet.getNmsMsgV_19());
				else
					msg=MessageComponent.parse(packet.getJsonV19());
				PlayerReceiveMsgEvent e=new PlayerReceiveMsgEvent(player,msg);
				Bukkit.getPluginManager().callEvent(e);
				if(e.isCancelled())
					c.set(true);
				else if(BukkitWrapper.version<19)
					packet.setNmsMsgV_19(e.msg.toNms());
				else
					packet.setJsonV19(e.msg.toString());
			}));
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
