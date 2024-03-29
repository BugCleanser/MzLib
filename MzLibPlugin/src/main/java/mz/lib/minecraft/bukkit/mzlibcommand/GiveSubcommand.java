package mz.lib.minecraft.bukkit.mzlibcommand;

import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import mz.lib.minecraft.bukkit.entity.PlayerUtil;
import mz.lib.minecraft.bukkit.item.MzItem;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

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
		MzLib.sendPluginMessage(sender,MzLib.instance,StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.give.success"),new MapEntry<>("%\\{item\\}",ItemStackBuilder.getDropNameWithNum(item.getItemStack().getRaw(),sender))));
	}
	
	@CommandHandler
	public void execute(CommandSender sender,Player target,MzItem item)
	{
		PlayerUtil.give(target,item.getItemStack().getRaw());
		MzLib.sendPluginMessage(sender,MzLib.instance,StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.give.other.success"),new MapEntry<>("%\\{player\\}",target.getName()),new MapEntry<>("%\\{item\\}",ItemStackBuilder.getDropNameWithNum(item.getItemStack().getRaw(),sender))));
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return LangUtil.getTranslated(sender,"mzlib.command.give.effect");
	}
}
