package mz.lib.minecraft.bukkitlegacy.command;

import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends AbsModule implements IMainCommand
{
	public @Deprecated
	Plugin plugin;
	public ArrayList<ICommandProcessor> subcommands=new ArrayList<>();
	ICommandProcessor processor;
	
	public MainCommand(Plugin plugin,ICommandProcessor processor)
	{
		super(plugin,IMainCommand.Module.instance);
		this.plugin=plugin;
		this.processor=processor;
	}
	
	@Override
	public List<String> executeOrUsages(CommandSender sender,String usedName,String[] args)
	{
		return IMainCommand.super.executeOrUsages(sender,usedName,args);
	}
	
	public Plugin getPlugin()
	{
		return plugin;
	}
	
	@Override
	public ICommandProcessor getProcessor()
	{
		return this.processor;
	}
	
	@Override
	public String getName()
	{
		return super.getName()+":"+processor.getNames()[0];
	}
}
