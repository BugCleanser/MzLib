package mz.lib.minecraft.bukkitlegacy.command.argparser;

import org.bukkit.command.CommandSender;

public class ByteArgParser extends AbsPrimitiveArgParser<Byte>
{
	public ByteArgParser()
	{
		super(Byte.class);
	}
	
	@Override
	public Byte parse(CommandSender sender,String arg) throws Throwable
	{
		return Byte.valueOf(arg);
	}
	
	@Override
	public boolean check(CommandSender sender,String arg,double min,double max)
	{
		try
		{
			byte value=parse(sender,arg);
			return value>=min&&value<=max;
		}
		catch(Throwable e)
		{
		}
		return false;
	}
	
	@Override
	public double getMax()
	{
		return Byte.MAX_VALUE;
	}
	
	@Override
	public double getMin()
	{
		return Byte.MIN_VALUE;
	}
	
	@Override
	public boolean checkFront(CommandSender sender,String arg,double min,double max)
	{
		return check(sender,arg,min,max);
	}
}
