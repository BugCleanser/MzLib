package mz.lib.minecraft.command.argparser;

import com.google.common.collect.Lists;
import mz.lib.minecraft.MinecraftLanguages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BoolArgParser extends AbsPrimitiveArgParser<Boolean>
{
	public BoolArgParser()
	{
		super(Boolean.class);
	}
	
	@Override
	public Boolean parse(CommandSender sender,String arg) throws Throwable
	{
		return arg.equalsIgnoreCase(MinecraftLanguages.get(sender,"mzlib.value.true"));
	}
	
	@Override
	public List<String> getDefaultPreset(CommandSender sender,double max,double min)
	{
		return Lists.newArrayList(MinecraftLanguages.get(sender,"mzlib.value.true"),MinecraftLanguages.get(sender,"mzlib.value.false"));
	}
	
	@Override
	public boolean checkFront(CommandSender sender,String arg,double min,double max)
	{
		return check(sender,arg,min,max);
	}
}
