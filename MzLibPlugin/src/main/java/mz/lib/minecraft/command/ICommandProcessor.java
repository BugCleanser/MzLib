package mz.lib.minecraft.command;

import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.MinecraftLanguages;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public interface ICommandProcessor
{
	String[] getNames();
	boolean matchName(String name);
	boolean isMustOp();
	void setMustOp(boolean mustOp);
	Permission getPermission();
	void setPermission(Permission permission);
	default String getEffect(CommandSender sender)
	{
		return "No such help, please touch the author";
	}
	List<String> onTabComplement(CommandSender sender,String usedName,String[] args);
	default List<String> executeOrUsages(CommandSender sender,String usedName,String[] args)
	{
		if(this.isMustOp()&&!sender.isOp())
		{
			sender.sendMessage(MinecraftLanguages.translate(sender,"mzlib.command.default.op.error"));
			return new ArrayList<>();
		}
		else if(!this.hasPermission(sender))
		{
			sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.translate(sender,"mzlib.command.default.permission.error"),new MapEntry<>("%\\{permission}",getPermission().getName())));
			return new ArrayList<>();
		}
		return null;
	}
	default boolean hasPermission(CommandSender sender)
	{
		if(isMustOp()&&!sender.isOp())
			return false;
		return getPermission()==null||sender.hasPermission(getPermission());
	}
}
