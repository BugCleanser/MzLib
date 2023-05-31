package mz.lib.minecraft.bukkitlegacy.command.argparser;

import com.google.common.collect.Lists;
import mz.lib.ListUtil;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkitlegacy.LangUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.stream.Collectors;

public class OfflinePlayerArgParser extends AbsArgParser<OfflinePlayer>
{
	public OfflinePlayerArgParser()
	{
		super(OfflinePlayer.class);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public OfflinePlayer parse(CommandSender sender,String arg) throws Throwable
	{
		return Bukkit.getOfflinePlayer(arg);
	}
	@Override
	public String getTypeName(CommandSender player,double max, double min)
	{
		return StringUtil.replaceStrings(LangUtil.getTranslated(player,"mzlib.command.default.type.offlinePlayer"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{min\\}",min+""),new MapEntry<>("%\\{max\\}",max+""))));
	}
	@Override
	public boolean checkFront(CommandSender sender, String arg,double min,double max)
	{
		for(OfflinePlayer p:Bukkit.getOfflinePlayers())
		{
			if(StringUtil.startsWithIgnoreCase(p.getName(),arg))
				return true;
		}
		return false;
	}
	
	@Override
	public java.util.List<String> getDefaultPreset(CommandSender player,double max,double min)
	{
		return Lists.newArrayList(Bukkit.getOfflinePlayers()).stream().map(p->p.getName()).collect(Collectors.toList());
	}
}
