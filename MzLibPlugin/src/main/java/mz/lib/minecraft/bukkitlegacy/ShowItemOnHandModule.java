package mz.lib.minecraft.bukkitlegacy;

import mz.lib.*;
import mz.lib.minecraft.bukkitlegacy.event.PlayerReceiveMsgEvent;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkitlegacy.message.MessageComponent;
import mz.lib.minecraft.bukkitlegacy.message.TextMessageComponent;
import mz.lib.minecraft.bukkitlegacy.message.TranslateMessageComponent;
import mz.lib.minecraft.bukkitlegacy.message.showonmouse.ShowItemOnMouse;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ShowItemOnHandModule extends AbsModule
{
	public static ShowItemOnHandModule instance=new ShowItemOnHandModule();
	private ShowItemOnHandModule()
	{
		super(MzLib.instance,ProtocolUtil.instance);
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		if(!MzLib.instance.getConfig().getBoolean("func.showItemOnHand.enable",true))
			return;
		if(StringUtil.containsIgnoreCase(MzLib.instance.getConfig().getStringList("func.showItemOnHand.commands"),event.getMessage().split(" ")[0].substring(1)))
		{
			AsyncPlayerChatEvent e = new AsyncPlayerChatEvent(false, event.getPlayer(), event.getMessage(), null);
			onPlayerChat(e);
			event.setMessage(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		if(!MzLib.instance.getConfig().getBoolean("func.showItemOnHand.enable",true))
			return;
		String msg=event.getMessage().replace("￼","[obj]");
		List<String> keys=MzLib.instance.getConfig().getStringList("func.showItemOnHand.keys");
		if(StringUtil.sum(msg,ListUtil.mergeLists(keys.stream().map(k->
		{
			List<String> r=new ArrayList<>();
			r.add(k);
			for(char i='1';i<='9';i++)
				r.add(k.replace('i',i));
			return r;
		}).toArray(List[]::new)))<=MzLib.instance.getConfig().getInt("func.showItemOnHand.max",5))
		{
			String n="￼"+event.getPlayer().getName()+"￼"+event.getPlayer().getInventory().getHeldItemSlot()+"￼";
			for(String key: keys)
				msg=msg.replace(key,n);
			for(char i='0';i<'9';i++)
			{
				n="￼"+event.getPlayer().getName()+"￼"+i+"￼";
				for(String key: keys)
					msg=msg.replace(key.replace('i',(char)(i+1)),n);
			}
		}
		event.setMessage(msg);
	}
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOW)
	public void onPlayerReceiveMsg(PlayerReceiveMsgEvent event)
	{
		if(MzLib.instance.getConfig().getBoolean("func.showItemOnHand.enable",true))
			decorate(event.msg,event.player);
	}
	
	public void decorate(MessageComponent msg,CommandSender sender)
	{
		if(msg instanceof TextMessageComponent)
		{
			String[] s=((TextMessageComponent) msg).text.split("￼",4);
			if(s.length==4)
			{
				((TextMessageComponent) msg).text=s[0];
				if(msg.extra==null)
					msg.extra=new ArrayList<>();
				Player pl=Bukkit.getPlayer(StringUtil.mergeStrings(s[1].split("§.")));
				if(pl!=null)
				{
					ItemStack item=ObcItemStack.ensure(pl.getInventory().getItem(Integer.parseInt(s[2]))).getRaw();
					msg.extra.add(0,new TextMessageComponent(StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.showItemOnHand.format"),new MapEntry<>("%\\{item\\}",ItemStackBuilder.getDropNameWithNum(item,sender)))).setShowOnMouse(new ShowItemOnMouse(item)));
				}
				msg.extra.add(1,new TextMessageComponent(s[3]));
			}
		}
		else if(msg instanceof TranslateMessageComponent)
		{
			if(((TranslateMessageComponent)msg).with!=null)
			{
				for(MessageComponent m:((TranslateMessageComponent)msg).with)
					decorate(m,sender);
			}
		}
		if(msg.extra!=null)
		{
			for(MessageComponent m:msg.extra)
				decorate(m,sender);
		}
	}
}
