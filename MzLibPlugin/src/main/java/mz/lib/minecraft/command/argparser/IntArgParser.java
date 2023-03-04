package mz.lib.minecraft.command.argparser;

import org.bukkit.command.CommandSender;

public class IntArgParser extends AbsPrimitiveArgParser<Integer>
{
	public IntArgParser()
	{
		super(Integer.class);
	}
	
	@Override
	public Integer parse(CommandSender sender,String arg) throws Throwable
	{
		return Integer.valueOf(arg);
	}
	
	@Override
	public double getMin()
	{
		return Integer.MIN_VALUE;
	}
	@Override
	public double getMax()
	{
		return Integer.MAX_VALUE;
	}
	
	@Override
	public boolean check(CommandSender sender,String arg,double min,double max)
	{
		try
		{
			int value=parse(sender,arg);
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
