package mz.lib.minecraft.bukkitlegacy.mzlibcommand;

import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.MinecraftLanguages;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.command.AbsLastCommandProcessor;
import mz.lib.minecraft.command.CommandHandler;
import mz.lib.minecraft.bukkitlegacy.entity.PlayerUtil;
import mz.lib.minecraft.bukkitlegacy.item.MzItem;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.*;

public class GiveSubcommand extends AbsLastCommandProcessor
{
	public static GiveSubcommand instance=new GiveSubcommand();
	public GiveSubcommand()
	{
		super(false,new Permission("mz.lib.command.give",PermissionDefault.OP),"give");
	}
	
	@CommandHandler
	public void execute(Player sender,MzItem item)
	{
		PlayerUtil.give(sender,item.getItemStack().getRaw());
		MzLib.sendPluginMessage(sender,MzLib.instance,StringUtil.replaceStrings(MinecraftLanguages.translate(sender,"mzlib.command.give.success"),new MapEntry<>("%\\{item\\}",ItemStackBuilder.getDropNameWithNum(item.getItemStack().getRaw(),sender))));
	}
	
	@CommandHandler
	public void execute(CommandSender sender,Player target,MzItem item)
	{
		PlayerUtil.give(target,item.getItemStack().getRaw());
		MzLib.sendPluginMessage(sender,MzLib.instance,StringUtil.replaceStrings(MinecraftLanguages.translate(sender,"mzlib.command.give.other.success"),new MapEntry<>("%\\{player\\}",target.getName()),new MapEntry<>("%\\{item\\}",ItemStackBuilder.getDropNameWithNum(item.getItemStack().getRaw(),sender))));
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return MinecraftLanguages.translate(sender,"mzlib.command.give.effect");
	}
}
