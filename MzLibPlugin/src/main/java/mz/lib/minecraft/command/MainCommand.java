package mz.lib.minecraft.command;

import mz.lib.module.MzModule;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends MzModule implements IMainCommand
{
	public @Deprecated
	Plugin plugin;
	public ArrayList<ICommandProcessor> subcommands=new ArrayList<>();
	ICommandProcessor processor;
	
	public MainCommand(Plugin plugin,ICommandProcessor processor)
	{
		this.plugin=plugin;
		this.processor=processor;
	}
	@Override
	public void onLoad()
	{
		depend(IMainCommand.Module.instance);
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

	public String getName()
	{
		return plugin.getName()+":"+processor.getNames()[0];
	}
}
