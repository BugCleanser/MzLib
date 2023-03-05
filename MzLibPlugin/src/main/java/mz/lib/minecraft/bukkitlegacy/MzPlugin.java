package mz.lib.minecraft.bukkitlegacy;

import com.google.common.collect.Lists;
import com.rylinaux.plugman.util.PluginUtil;
import mz.lib.*;
import mz.lib.minecraft.*;
import mz.lib.minecraft.message.MessageComponent;
import mz.lib.minecraft.message.TextMessageComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.stream.*;

public class MzPlugin extends JavaPlugin
{
	public void sendPluginMessage(CommandSender sender,String msg)
	{
		sendPluginMessage(sender,this,msg);
	}
	public static void sendPluginMessage(CommandSender sender,Plugin p,String msg)
	{
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.plugin.message"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{plugin}",p.getName()),new MapEntry<>("%\\{msg}",msg)))));
	}
	public void sendPluginMessage(CommandSender sender,MessageComponent msg)
	{
		sendPluginMessage(sender,this,msg);
	}
	public static void sendPluginMessage(CommandSender sender,Plugin p,MessageComponent msg)
	{
		String[] msgs=(" "+StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.plugin.message"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{plugin}",p.getName()))))+" ").split("%\\{msg}");
		msgs[0]=msgs[0].substring(1);
		msgs[msgs.length-1]=msgs[msgs.length-1].substring(0,msgs[msgs.length-1].length()-1);
		List<MessageComponent> m=Lists.newArrayList(new TextMessageComponent(msgs[0]));
		for(int i=1;i<msgs.length;i++)
		{
			m.add(msg);
			m.add(new TextMessageComponent(msgs[i]));
		}
		MessageComponent.merge(m.toArray(new MessageComponent[0])).send(sender);
	}
	
	@Override
	public void onEnable()
	{
		if(Boolean.parseBoolean(System.getProperty(getName()+".disabled","false")))
		{
			System.setProperty(getName()+".disabled","false");
			if(Bukkit.getPluginManager().isPluginEnabled("PlugMan"))
				for(Plugin p: Bukkit.getPluginManager().getPlugins())
				{
					if(StringUtil.containsIgnoreCase(p.getDescription().getDepend(),this.getName()))
						PluginUtil.reload(p);
				}
		}
	}
	
	@Override
	public void onDisable()
	{
		System.setProperty(getName()+".disabled","true");
		for(Plugin p: Bukkit.getPluginManager().getPlugins())
		{
			for(Plugin d: p.getDescription().getDepend().stream().map(d->Bukkit.getPluginManager().getPlugin(d)).collect(Collectors.toList()))
			{
				if(d==this)
				{
					if(p.isEnabled())
						Bukkit.getPluginManager().disablePlugin(p);
					break;
				}
			}
		}
	}
	
	@Override
	public File getFile()
	{
		return super.getFile();
	}
}
