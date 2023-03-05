package mz.lib.minecraft.bukkitlegacy.item;

import mz.lib.*;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkitlegacy.message.*;
import mz.lib.minecraft.message.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import java.util.*;

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
		MzLib.sendPluginMessage(user,MzLib.instance,MinecraftLanguages.get(user,"mzlib.item.debugFish.blockinfo"));
		List<MessageComponent> msg=new ArrayList<>();
		msg.add(TextMessageComponent.textCopy(StringUtil.replaceStrings(MinecraftLanguages.get(user,"mzlib.item.debugFish.blockinfo.material"),new MapEntry<>("%\\{material}",target.getType().toString())),user,target.getType().toString()));
		MessageComponent.mergeLines(msg.toArray(new MessageComponent[0])).send(user);
	}
	@Override
	default void onUse(Player user,EquipmentSlot hand,Ref<Boolean> cancelled,Entity target)
	{
		MzLib.sendPluginMessage(user,MzLib.instance,MinecraftLanguages.get(user,"mzlib.item.debugFish.entityinfo"));
		List<MessageComponent> msg=new ArrayList<>();
		msg.add(TextMessageComponent.textCopy(StringUtil.replaceStrings(MinecraftLanguages.get(user,"mzlib.item.debugFish.entityinfo.entitytype"),new MapEntry<>("%\\{entitytype}",target.getType().toString())),user,target.getType().toString()));
		MessageComponent.mergeLines(msg.toArray(new MessageComponent[0])).send(user);
	}
}
