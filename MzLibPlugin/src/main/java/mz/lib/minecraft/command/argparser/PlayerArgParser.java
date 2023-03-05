package mz.lib.minecraft.command.argparser;

import com.google.common.collect.Lists;
import mz.lib.ListUtil;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.MinecraftLanguages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerArgParser extends AbsArgParser<Player>
{
	public PlayerArgParser()
	{
		super(Player.class);
	}
	
	@Override
	public Player parse(CommandSender sender, String arg) throws Throwable
	{
		return Bukkit.getPlayer(arg);
	}
	@Override
	public String getTypeName(CommandSender player,double max, double min)
	{
		return StringUtil.replaceStrings(MinecraftLanguages.get(player,"mzlib.command.default.type.player"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{min\\}",min+""),new MapEntry<>("%\\{max\\}",max+""))));
	}
	@Override
	public boolean checkFront(CommandSender sender, String arg,double min,double max)
	{
		for(Player p:Bukkit.getOnlinePlayers())
		{
			if(StringUtil.startsWithIgnoreCase(p.getName(),arg))
				return true;
		}
		return false;
	}
	
	@Override
	public List<String> getDefaultPreset(CommandSender player,double max,double min)
	{
		return Bukkit.getOnlinePlayers().stream().map(p->p.getName()).collect(Collectors.toList());
	}
}
