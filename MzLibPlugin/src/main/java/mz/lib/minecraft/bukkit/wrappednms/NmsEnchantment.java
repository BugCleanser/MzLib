package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.Enchantment",maxVer=17),@VersionName(value="net.minecraft.world.item.enchantment.Enchantment",minVer=17)})
public interface NmsEnchantment extends WrappedBukkitObject
{
	static NmsRegistryMaterials getEnchantsV_13()
	{
		return WrappedObject.wrap(NmsEnchantment.class,null).staticGetEnchantsV_13();
	}
	@WrappedBukkitFieldAccessor(@VersionName(maxVer=13, value="enchantments"))
	NmsRegistryMaterials staticGetEnchantsV_13();
	@WrappedBukkitMethod(@VersionName(value="a",maxVer=13))
	String getTranslateKeyV_13();
	@WrappedBukkitMethod(@VersionName(value="g",minVer=13))
	String getTranslateKeyV13();
	default String getTranslateKey()
	{
		if(BukkitWrapper.v13)
			return getTranslateKeyV13();
		else
			return getTranslateKeyV_13();
	}
	
	@WrappedBukkitClass({@VersionName(value="nms.Enchantment$Rarity",maxVer=17),@VersionName(value="net.minecraft.world.item.enchantment.Enchantment$Rarity",minVer=17)})
	interface Rarity extends WrappedObject
	{
	}
}
