package mz.lib.minecraft.command;

import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkitlegacy.LangUtil;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public interface ILastCommandProcessor extends ICommandProcessor
{
	List<FinalCommandExecutor> getExecutors();
	
	@Override
	default List<String> onTabComplement(CommandSender sender,String usedName,String[] args)
	{
		List<String> r=new LinkedList<>();
		boolean ce=false;
		for(FinalCommandExecutor e: getExecutors())
		{
			if(e.canTabComplement(sender,args))
			{
				if(e.hasPermission(sender))
				{
					r.addAll(e.onTabComplement(sender,args));
					ce=true;
				}
			}
		}
		if(!ce)
		{
			for(FinalCommandExecutor e: getExecutors())
			{
				if(e.hasPermission(sender))
					if(e.checkArgs(sender,TypeUtil.cast(ArrayUtils.subarray(args,0,args.length-1))))
						r.addAll(e.onTabComplement(sender,args));
			}
			if(r.isEmpty())
				r.add("");
		}
		return r;
	}
	
	@Override
	default List<String> executeOrUsages(CommandSender sender,String usedName,String[] args)
	{
		List<String> su=ICommandProcessor.super.executeOrUsages(sender,usedName,args);
		if(su!=null)
			return su;
		for(FinalCommandExecutor e: getExecutors())
		{
			if(e.canExecute(sender,args))
			{
				e.execute(sender,args);
				return null;
			}
		}
		LinkedList<String> r=new LinkedList<>();
		for(FinalCommandExecutor e: getExecutors())
		{
			if(e.hasPermission(sender))
				r.add(StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.default.usage.args"),new MapEntry<>("%\\{cmd}",usedName),new MapEntry<>("%\\{args}",e.getUsage(sender))));
		}
		return r;
	}
}
