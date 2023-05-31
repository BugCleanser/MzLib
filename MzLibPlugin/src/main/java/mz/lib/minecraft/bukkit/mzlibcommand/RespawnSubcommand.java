package mz.lib.minecraft.bukkit.mzlibcommand;

import com.google.common.collect.Lists;
import mz.lib.ListUtil;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.*;

public class RespawnSubcommand extends AbsLastCommandProcessor
{
	public static RespawnSubcommand instance=new RespawnSubcommand();
	public RespawnSubcommand()
	{
		super(false,new Permission("mz.lib.command.respawn",PermissionDefault.OP),"respawn","revive");
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return LangUtil.getTranslated(sender,"mzlib.command.respawn.effect");
	}
	
	@CommandHandler
	public void execute(CommandSender sender,Player player)
	{
		player.spigot().respawn();
		MzLib.sendPluginMessage(sender,MzLib.instance,StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.respawn.success"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{player\\}",player.getName())))));
	}
}
