package mz.lib.minecraft.bukkitlegacy.command;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.LinkedList;
import java.util.List;

public abstract class AbsFrontCommandProcessor extends AbsCommandProcessor implements IFrontCommandProcessor
{
	public LinkedList<ICommandProcessor> subcommands=new LinkedList<>();
	
	public AbsFrontCommandProcessor(boolean mustOp,Permission permission,String... names)
	{
		super(mustOp,permission,names);
	}
	
	@Override
	public List<ICommandProcessor> getSubcommands()
	{
		return subcommands;
	}
	
	@Override
	public List<String> executeOrUsages(CommandSender sender,String usedName,String[] args)
	{
		return IFrontCommandProcessor.super.executeOrUsages(sender,usedName,args);
	}
}
