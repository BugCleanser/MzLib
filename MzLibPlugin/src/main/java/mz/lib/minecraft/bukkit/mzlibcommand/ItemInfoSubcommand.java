package mz.lib.minecraft.bukkit.mzlibcommand;

import com.google.common.collect.Lists;
import mz.lib.ListUtil;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.message.MessageComponent;
import mz.lib.minecraft.bukkit.message.TextMessageComponent;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.*;

import java.util.ArrayList;
import java.util.List;

public class ItemInfoSubcommand extends AbsLastCommandProcessor
{
	public static ItemInfoSubcommand instance=new ItemInfoSubcommand();
	public ItemInfoSubcommand()
	{
		super(false,new Permission("mz.lib.command.iteminfo",PermissionDefault.TRUE),"iteminfo");
	}
	
	@CommandHandler
	public void execute(Player sender)
	{
		ItemStack is=sender.getInventory().getItemInMainHand();
		if(!ItemStackBuilder.isAir(is))
		{
			ItemStackBuilder isb=new ItemStackBuilder(is);
			MzLib.sendPluginMessage(sender,MzLib.instance,LangUtil.getTranslated(sender,"mzlib.command.iteminfo"));
			List<MessageComponent> msg=new ArrayList<>();
			msg.add(TextMessageComponent.textCopy(StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.iteminfo.id"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{id\\}",isb.getId())))),sender,isb.getId()));
			msg.add(TextMessageComponent.textCopy("\n"+StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.iteminfo.material"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{material\\}",is.getType().name())))),sender,is.getType().name()));
			msg.add(TextMessageComponent.textCopy("\n"+StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.iteminfo.nameKey"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{nameKey\\}",ItemStackBuilder.getTranslateKey(is))))),sender,ItemStackBuilder.getTranslateKey(is)));
			msg.add(TextMessageComponent.textCopy("\n"+StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.iteminfo.name"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{locale\\}",sender.getLocale()),new MapEntry<>("%\\{name\\}",LangUtil.getTranslated(sender,ItemStackBuilder.getTranslateKey(is)))))),sender,LangUtil.getTranslated(sender,ItemStackBuilder.getTranslateKey(is))));
			if(!BukkitWrapper.v13)
			{
				msg.add(TextMessageComponent.textCopy("\n"+StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.iteminfo.numId"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{numId\\}",isb.get().getType().getId()+"")))),sender,isb.get().getType().getId()+""));
				msg.add(TextMessageComponent.textCopy("\n"+StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.iteminfo.childId"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{childId\\}",isb.getChildId()+"")))),sender,isb.getChildId()+""));
			}
			msg.add(TextMessageComponent.textCopy("\n"+StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.iteminfo.num"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{num\\}",isb.getCount()+"")))),sender,isb.getCount()+""));
			if(isb.hasTag())
				msg.add(TextMessageComponent.textCopy("\n"+StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.command.iteminfo.tag"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{tag\\}",isb.tag().toString())))),sender,isb.tag().toString()));
			MessageComponent.merge(msg.toArray(new MessageComponent[0])).send(sender);
		}
		else
			MzLib.sendPluginMessage(sender,MzLib.instance,LangUtil.getTranslated(sender,"mzlib.command.iteminfo.error"));
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return LangUtil.getTranslated(sender,"mzlib.command.iteminfo.effect");
	}
}
