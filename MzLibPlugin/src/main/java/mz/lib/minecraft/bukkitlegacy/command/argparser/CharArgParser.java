package mz.lib.minecraft.bukkitlegacy.command.argparser;

import org.bukkit.command.CommandSender;

public class CharArgParser extends AbsPrimitiveArgParser<Character>
{
	public CharArgParser()
	{
		super(Character.class);
	}
	
	@Override
	public Character parse(CommandSender sender,String arg) throws Throwable
	{
		if(arg.length()>1)
			throw new ArrayIndexOutOfBoundsException();
		return arg.charAt(0);
	}
	
	@Override
	public boolean checkFront(CommandSender sender,String arg,double min,double max)
	{
		return check(sender,arg,min,max);
	}
}
