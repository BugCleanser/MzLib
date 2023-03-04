package mz.lib.minecraft.command.argparser;

import org.bukkit.command.CommandSender;

public class ShortArgParser extends AbsPrimitiveArgParser<Short>
{
	public ShortArgParser()
	{
		super(Short.class);
	}
	
	@Override
	public Short parse(CommandSender sender,String arg) throws Throwable
	{
		return Short.valueOf(arg);
	}
	
	@Override
	public double getMin()
	{
		return Short.MIN_VALUE;
	}
	@Override
	public double getMax()
	{
		return Short.MAX_VALUE;
	}
	
	@Override
	public boolean check(CommandSender sender,String arg,double min,double max)
	{
		try
		{
			short value=parse(sender,arg);
			return value>=min && value<=max;
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
