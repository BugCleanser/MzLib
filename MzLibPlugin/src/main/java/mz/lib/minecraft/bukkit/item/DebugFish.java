package mz.lib.minecraft.bukkit.item;

import mz.lib.MapEntry;
import mz.lib.Ref;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.message.MessageComponent;
import mz.lib.minecraft.bukkit.message.TextMessageComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public interface DebugFish extends MzItem
{
	@Override
	default NamespacedKey getKey()
	{
		return new NamespacedKey(MzLib.instance,"debug_fish");
	}
	@Override
	default String getRawIdV_13()
	{
		return "minecraft:fish";
	}
	@Override
	default int getChildIdV_13()
	{
		return 2;
	}
	@Override
	default String getRawIdV13()
	{
		return "minecraft:tropical_fish";
	}
	@Override
	default String getTranslatedKey()
	{
		return "mzlib.item.debugFish";
	}
	
	@Override
	default void onUse(Player user,EquipmentSlot hand,Ref<Boolean> cancelled)
	{
		cancelled.set(true);
	}
	@Override
	default void onUse(Player user,EquipmentSlot hand,Ref<Boolean> cancelled,Block target,BlockFace face)
	{
		MzLib.sendPluginMessage(user,MzLib.instance,LangUtil.getTranslated(user,"mzlib.item.debugFish.blockinfo"));
		List<MessageComponent> msg=new ArrayList<>();
		msg.add(TextMessageComponent.textCopy(StringUtil.replaceStrings(LangUtil.getTranslated(user,"mzlib.item.debugFish.blockinfo.material"),new MapEntry<>("%\\{material}",target.getType().toString())),user,target.getType().toString()));
		MessageComponent.mergeLines(msg.toArray(new MessageComponent[0])).send(user);
	}
	@Override
	default void onUse(Player user,EquipmentSlot hand,Ref<Boolean> cancelled,Entity target)
	{
		MzLib.sendPluginMessage(user,MzLib.instance,LangUtil.getTranslated(user,"mzlib.item.debugFish.entityinfo"));
		List<MessageComponent> msg=new ArrayList<>();
		msg.add(TextMessageComponent.textCopy(StringUtil.replaceStrings(LangUtil.getTranslated(user,"mzlib.item.debugFish.entityinfo.entitytype"),new MapEntry<>("%\\{entitytype}",target.getType().toString())),user,target.getType().toString()));
		MessageComponent.mergeLines(msg.toArray(new MessageComponent[0])).send(user);
	}
}
