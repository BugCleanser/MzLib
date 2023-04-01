package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedObject;
import mz.lib.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.Enchantment",maxVer=17),@VersionalName(value="net.minecraft.world.item.enchantment.Enchantment",minVer=17)})
public interface NmsEnchantment extends VersionalWrappedObject
{
	static NmsRegistryMaterials getEnchantsV_13()
	{
		return WrappedObject.wrap(NmsEnchantment.class,null).staticGetEnchantsV_13();
	}
	@VersionalWrappedFieldAccessor(@VersionalName(maxVer=13, value="enchantments"))
	NmsRegistryMaterials staticGetEnchantsV_13();
	@VersionalWrappedMethod(@VersionalName(value="a",maxVer=13))
	String getTranslateKeyV_13();
	@VersionalWrappedMethod(@VersionalName(value="g",minVer=13))
	String getTranslateKeyV13();
	default String getTranslateKey()
	{
		if(Server.instance.v13)
			return getTranslateKeyV13();
		else
			return getTranslateKeyV_13();
	}
	
	@VersionalWrappedClass({@VersionalName(value="nms.Enchantment$Rarity",maxVer=17),@VersionalName(value="net.minecraft.world.item.enchantment.Enchantment$Rarity",minVer=17)})
	interface Rarity extends WrappedObject
	{
	}
}
