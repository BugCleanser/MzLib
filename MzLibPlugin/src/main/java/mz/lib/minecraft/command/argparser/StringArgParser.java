package mz.lib.minecraft.command.argparser;

import mz.lib.minecraft.bukkitlegacy.LangUtil;
import org.bukkit.command.CommandSender;

public class StringArgParser extends AbsArgParser<String>
{
	public StringArgParser()
	{
		super(String.class);
	}
	
	@Override
	public String parse(CommandSender sender,String arg) throws Throwable
	{
		return arg;
	}
	
	@Override
	public boolean checkFront(CommandSender sender,String arg,double min,double max)
	{
		return true;
	}
	
	@Override
	public String getTypeName(CommandSender player,double max,double min)
	{
		return LangUtil.getTranslated(player,"mzlib.command.default.type.string");
	}
	
	@Override
	public boolean canContainWhitespace()
	{
		return true;
	}
}
