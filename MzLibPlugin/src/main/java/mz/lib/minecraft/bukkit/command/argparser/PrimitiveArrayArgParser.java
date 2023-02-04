package mz.lib.minecraft.bukkit.command.argparser;

import mz.lib.TypeUtil;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Array;

public class PrimitiveArrayArgParser<T> extends AbsArrayArgParser<Object>
{
	public PrimitiveArrayArgParser(Class<T> type)
	{
		super(TypeUtil.cast(TypeUtil.toWrapper(type)));
	}
	
	@Override
	public T parse(CommandSender sender,String arg) throws Throwable
	{
		return TypeUtil.cast(TypeUtil.toPrimitiveArray(parse0(sender,arg)));
	}
	
	@Override
	public boolean checkFront(CommandSender sender,String arg,double min,double max)
	{
		return false;
	}
}
