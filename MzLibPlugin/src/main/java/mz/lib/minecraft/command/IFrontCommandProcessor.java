package mz.lib.minecraft.command;

import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.TypeUtil;
import mz.lib.minecraft.MinecraftLanguages;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface IFrontCommandProcessor extends ICommandProcessor
{
	List<ICommandProcessor> getSubcommands();
	
	@Override
	default List<String> executeOrUsages(CommandSender sender,String usedName,String[] args)
	{
		List<String> su=ICommandProcessor.super.executeOrUsages(sender,usedName,args);
		if(su!=null)
			return su;
		if(args.length>0)
			for(ICommandProcessor s: getSubcommands())
			{
				if(s.matchName(args[0]))
				{
					return s.executeOrUsages(sender,usedName+" "+args[0],TypeUtil.cast(ArrayUtils.subarray(args,1,args.length)));
				}
			}
		List<String> r=new ArrayList<>();
		for(ICommandProcessor s: getSubcommands())
		{
			if(s.hasPermission(sender))
				r.add(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.default.usage.part"),new MapEntry<>("%\\{cmd}",usedName+" "+s.getNames()[0]),new MapEntry<>("%\\{effect}",s.getEffect(sender))));
		}
		return r;
	}
	
	@Override
	default List<String> onTabComplement(CommandSender player,String usedName,String[] args)
	{
		if(args.length==1)
		{
			List<String> r=new LinkedList<>();
			for(ICommandProcessor s: getSubcommands())
			{
				if(s.hasPermission(player))
					for(String n: s.getNames())
					{
						if(StringUtil.startsWithIgnoreCase(n,args[0]))
						{
							r.add(n);
							break;
						}
					}
			}
			return r;
		}
		String[] cargs=TypeUtil.cast(ArrayUtils.subarray(args,1,args.length));
		for(ICommandProcessor s: getSubcommands())
		{
			if(s.matchName(args[0]))
			{
				if(s.hasPermission(player))
					return s.onTabComplement(player,args[0],cargs);
			}
		}
		return new ArrayList<>();
	}
}
