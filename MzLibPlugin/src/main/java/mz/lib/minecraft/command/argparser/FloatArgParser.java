package mz.lib.minecraft.command.argparser;

import org.bukkit.command.CommandSender;

public class FloatArgParser extends AbsPrimitiveArgParser<Float>
{
	public FloatArgParser()
	{
		super(Float.class);
	}
	
	@Override
	public Float parse(CommandSender sender,String arg) throws Throwable
	{
		return Float.valueOf(arg);
	}
	
	@Override
	public double getMax()
	{
		return Float.POSITIVE_INFINITY;
	}
	
	@Override
	public double getMin()
	{
		return Float.NEGATIVE_INFINITY;
	}
	
	@Override
	public boolean check(CommandSender sender,String arg,double min,double max)
	{
		try
		{
			float value=parse(sender,arg);
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
