package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.EnchantmentSlotType",maxVer=17),@VersionalName(value="net.minecraft.world.item.enchantment.EnchantmentSlotType",minVer=17)})
public interface NmsEnchantmentSlotType extends VersionalWrappedObject
{
	static NmsEnchantmentSlotType getArmor()
	{
		return WrappedObject.wrap(NmsEnchantmentSlotType.class,null).staticGetArmor();
	}
	static NmsEnchantmentSlotType getArmorFeet()
	{
		return WrappedObject.wrap(NmsEnchantmentSlotType.class,null).staticGetArmorFeet();
	}
	static NmsEnchantmentSlotType getArmorLegs()
	{
		return WrappedObject.wrap(NmsEnchantmentSlotType.class,null).staticGetArmorLegs();
	}
	static NmsEnchantmentSlotType getArmorChest()
	{
		return WrappedObject.wrap(NmsEnchantmentSlotType.class,null).staticGetArmorChest();
	}
	static NmsEnchantmentSlotType getArmorHead()
	{
		return WrappedObject.wrap(NmsEnchantmentSlotType.class,null).staticGetArmorHead();
	}
	static NmsEnchantmentSlotType getFishingRod()
	{
		return WrappedObject.wrap(NmsEnchantmentSlotType.class,null).staticGetFishingRod();
	}
	static NmsEnchantmentSlotType getBreakable()
	{
		return WrappedObject.wrap(NmsEnchantmentSlotType.class,null).staticGetBreakable();
	}
	static NmsEnchantmentSlotType getBow()
	{
		return WrappedObject.wrap(NmsEnchantmentSlotType.class,null).staticGetBow();
	}
	static NmsEnchantmentSlotType getWeapon()
	{
		return WrappedObject.wrap(NmsEnchantmentSlotType.class,null).staticGetWeapon();
	}
	
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="ARMOR"),@VersionalName(minVer=17, value="a")})
	NmsEnchantmentSlotType staticGetArmor();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="ARMOR_FEET"),@VersionalName(minVer=17, value="b")})
	NmsEnchantmentSlotType staticGetArmorFeet();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="ARMOR_LEGS"),@VersionalName(minVer=17, value="c")})
	NmsEnchantmentSlotType staticGetArmorLegs();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="ARMOR_CHEST"),@VersionalName(minVer=17, value="d")})
	NmsEnchantmentSlotType staticGetArmorChest();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="ARMOR_HEAD"),@VersionalName(minVer=17, value="e")})
	NmsEnchantmentSlotType staticGetArmorHead();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="FISHING_ROD"),@VersionalName(minVer=17, value="h")})
	NmsEnchantmentSlotType staticGetFishingRod();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="BREAKABLE"),@VersionalName(minVer=17, value="j")})
	NmsEnchantmentSlotType staticGetBreakable();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="BOW"),@VersionalName(minVer=17, value="k")})
	NmsEnchantmentSlotType staticGetBow();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="WEAPON"),@VersionalName(minVer=17, value="l")})
	NmsEnchantmentSlotType staticGetWeapon();
}
