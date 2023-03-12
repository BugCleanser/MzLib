package mz.lib.minecraft.mzlibcommand;

import com.google.common.collect.Lists;
import mz.lib.ListUtil;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.MinecraftLanguages;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.command.AbsLastCommandProcessor;
import mz.lib.minecraft.command.CommandHandler;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.*;

public class PlayerInfoSubcommand extends AbsLastCommandProcessor
{
	public static PlayerInfoSubcommand instance=new PlayerInfoSubcommand();
	public Permission permissionOther=new Permission("mz.lib.command.playerinfo.other",PermissionDefault.OP);
	public PlayerInfoSubcommand()
	{
		super(false,new Permission("mz.lib.command.playerinfo",PermissionDefault.TRUE),"playerinfo");
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return MinecraftLanguages.get(sender,"mzlib.command.playerinfo.effect");
	}
	
	@CommandHandler
	public void execute(Player player)
	{
		execute(player,player);
	}
	
	@CommandHandler(permission="mz.lib.command.playerinfo.other")
	public void execute(CommandSender sender,Player player)
	{
		MzLib.sendPluginMessage(sender,MzLib.instance,MinecraftLanguages.get(sender,"mzlib.command.playerinfo"));
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.playerinfo.name"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{name\\}",player.getName())))));
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.playerinfo.id"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{id\\}",player.getEntityId()+"")))));
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.playerinfo.uid"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{uid\\}",player.getUniqueId()+"")))));
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.playerinfo.locale"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{locale\\}",player.getLocale())))));
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.playerinfo.shift"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{shift\\}",MinecraftLanguages.get(sender,player.isSneaking()?"mzlib.value.true":"mzlib.value.false"))))));
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.playerinfo.walkSpeed"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{walkSpeed\\}",player.getWalkSpeed()+"")))));
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.playerinfo.flySpeed"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{flySpeed\\}",player.getFlySpeed()+"")))));
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.playerinfo.health"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{health\\}",player.getHealth()+"")))));
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.playerinfo.mainHand"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{nbt\\}",new ItemStackBuilder(player.getInventory().getItemInMainHand()).toString())))));
		sender.sendMessage(StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.playerinfo.offHand"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{nbt\\}",new ItemStackBuilder(player.getInventory().getItemInOffHand()).toString())))));
	}
}
