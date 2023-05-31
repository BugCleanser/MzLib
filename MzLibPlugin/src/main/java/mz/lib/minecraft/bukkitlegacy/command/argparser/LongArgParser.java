package mz.lib.minecraft.bukkitlegacy.command.argparser;

import org.bukkit.command.CommandSender;

public class LongArgParser extends AbsPrimitiveArgParser<Long>
{
	public LongArgParser()
	{
		super(Long.class);
	}
	
	@Override
	public Long parse(CommandSender sender,String arg) throws Throwable
	{
		return Long.valueOf(arg);
	}
	
	@Override
	public double getMax()
	{
		return Long.MAX_VALUE;
	}
	@Override
	public double getMin()
	{
		return Long.MIN_VALUE;
	}
	
	@Override
	public boolean check(CommandSender sender,String arg,double min,double max)
	{
		try
		{
			long value=parse(sender,arg);
			return value>=min&&value<=max;
		}
		catch(Throwable e)
		{
		}
		return false;
	}
	
	@Override
	public boolean checkFront(CommandSender sender,String arg,double min,double max)
	{
		return check(sender,arg,min,max);
	}
}
