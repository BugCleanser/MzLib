package mz.lib.minecraft.bukkit.mzlibcommand;

import com.google.common.collect.Lists;
import mz.lib.ListUtil;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class SundayCommand extends AbsLastCommandProcessor
{
	public SundayCommand()
	{
		super(true,null,"sunday");
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return LangUtil.getTranslated(sender,"mzlib.command.sunday.effect");
	}
	
	@CommandHandler
	public void execute(CommandSender sender)
	{
		World w=sender instanceof Entity ?((Entity) sender).getWorld(): Bukkit.getWorlds().get(0);
		w.setTime(1000);
		w.setStorm(false);
		w.setThundering(false);
		MzLib.sendPluginMessage(sender,MzLib.instance,StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.sunday.success"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{world\\}",w.getName())))));
	}
}
