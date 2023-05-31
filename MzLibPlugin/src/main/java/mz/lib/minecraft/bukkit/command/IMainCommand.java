package mz.lib.minecraft.bukkit.command;

import com.google.common.collect.Lists;
import mz.lib.ListMap;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.module.IRegistrar;
import mz.lib.minecraft.bukkit.module.RegistrarRegistrar;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IMainCommand extends TabExecutor
{
	static class Module extends AbsModule implements IRegistrar<IMainCommand>
	{
		public static Module instance=new Module();
		
		public Constructor<PluginCommand> newPluginCommand;
		public SimpleCommandMap commandMap;
		public Field SimpleCommandMapKnownCommands;
		public Map<String,Command> knownCommands;
		
		public Module()
		{
			super(MzLib.instance,RegistrarRegistrar.instance);
			
			try
			{
				newPluginCommand=PluginCommand.class.getDeclaredConstructor(String.class,Plugin.class);
				newPluginCommand.setAccessible(true);
				Field cmc=Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
				cmc.setAccessible(true);
				commandMap=TypeUtil.cast(cmc.get(Bukkit.getPluginManager()));
				SimpleCommandMapKnownCommands=SimpleCommandMap.class.getDeclaredField("knownCommands");
				SimpleCommandMapKnownCommands.setAccessible(true);
				knownCommands=TypeUtil.cast(SimpleCommandMapKnownCommands.get(commandMap));
			}
			catch(Throwable e)
			{
				throw TypeUtil.throwException(e);
			}
		}
		
		@Override
		public Class<IMainCommand> getType()
		{
			return IMainCommand.class;
		}
		@Override
		public boolean register(IMainCommand obj)
		{
			commands.add(obj.getPlugin(),obj);
			regCommand(obj);
			return true;
		}
		@Override
		public void unregister(IMainCommand obj)
		{
			unregCommand(obj);
			commands.removeValue(obj);
		}
		
		public void regCommand(IMainCommand command)
		{
			try
			{
				PluginCommand pc=newPluginCommand.newInstance(command.getProcessor().getNames()[0],command.getPlugin());
				pc.setAliases(Lists.newArrayList(command.getProcessor().getNames()));
				if(command.getProcessor().getNames().length>1)
				{
					List<String> l=Lists.newArrayList(command.getProcessor().getNames());
					l.remove(0);
					pc.setAliases(l);
				}
				commandMap.register(command.getPlugin().getName().toLowerCase(),pc);
				pc.setTabCompleter(command);
				pc.setExecutor(command);
			}
			catch(Throwable e)
			{
				throw TypeUtil.throwException(e);
			}
		}
		public void unregCommand(IMainCommand command)
		{
			PluginCommand pc=Bukkit.getPluginCommand(command.getProcessor().getNames()[0]);
			pc.setExecutor(null);
			pc.setTabCompleter(null);
			pc.unregister(commandMap);
			knownCommands=new HashMap<>(knownCommands);
			for(String name:command.getProcessor().getNames())
			{
				knownCommands.remove(name.toLowerCase());
				knownCommands.remove((command.getPlugin().getName()+":"+name).toLowerCase());
			}
			try
			{
				SimpleCommandMapKnownCommands.set(commandMap,knownCommands);
			}
			catch(Throwable e)
			{
				throw TypeUtil.throwException(e);
			}
			commands.remove(command);
		}
	}
	static ListMap<Plugin,IMainCommand> commands=new ListMap<>(new HashMap<>());
	
	Plugin getPlugin();
	ICommandProcessor getProcessor();
	default List<String> executeOrUsages(CommandSender sender,String usedName,String[] args)
	{
		return getProcessor().executeOrUsages(sender,usedName,args);
	}
	@Override
	default List<String> onTabComplete(CommandSender sender,Command command,String alias,String[] args)
	{
		return getProcessor().onTabComplement(sender,alias,args);
	}
	default boolean matchName(String name)
	{
		return getProcessor().matchName(name);
	}
	
	@Override
	default boolean onCommand(CommandSender sender,org.bukkit.command.Command paramCommand,String label,String[] args)
	{
		List<String> usage=executeOrUsages(sender,label,args);
		if(usage!=null&&usage.size()>0)
		{
			MzLib.sendPluginMessage(sender,getPlugin(),LangUtil.getTranslated(sender,"mzlib.command.default.usage"));
			usage.forEach(sender::sendMessage);
		}
		return false;
	}
}
