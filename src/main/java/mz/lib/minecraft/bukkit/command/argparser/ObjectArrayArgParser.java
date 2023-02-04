package mz.lib.minecraft.bukkit.command.argparser;

import org.bukkit.command.CommandSender;

public class ObjectArrayArgParser<T> extends AbsArrayArgParser<T>
{
	public ObjectArrayArgParser(Class<T> type)
	{
		super(type);
	}
	
	@Override
	public T[] parse(CommandSender sender,String arg) throws Throwable
	{
		return parse0(sender,arg);
	}
}
