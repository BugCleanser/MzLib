package mz.lib.minecraft.command.argparser;

import org.bukkit.command.CommandSender;

public class DoubleArgParser extends AbsPrimitiveArgParser<Double>
{
	public DoubleArgParser()
	{
		super(Double.class);
	}
	
	@Override
	public Double parse(CommandSender sender,String arg) throws Throwable
	{
		return Double.valueOf(arg);
	}
	
	@Override
	public boolean check(CommandSender sender,String arg,double min,double max)
	{
		try
		{
			double value=parse(sender,arg);
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
