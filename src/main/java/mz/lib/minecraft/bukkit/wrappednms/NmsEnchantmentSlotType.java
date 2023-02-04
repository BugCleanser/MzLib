package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.EnchantmentSlotType",maxVer=17),@VersionName(value="net.minecraft.world.item.enchantment.EnchantmentSlotType",minVer=17)})
public interface NmsEnchantmentSlotType extends WrappedBukkitObject
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
	
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="ARMOR"),@VersionName(minVer=17, value="a")})
	NmsEnchantmentSlotType staticGetArmor();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="ARMOR_FEET"),@VersionName(minVer=17, value="b")})
	NmsEnchantmentSlotType staticGetArmorFeet();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="ARMOR_LEGS"),@VersionName(minVer=17, value="c")})
	NmsEnchantmentSlotType staticGetArmorLegs();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="ARMOR_CHEST"),@VersionName(minVer=17, value="d")})
	NmsEnchantmentSlotType staticGetArmorChest();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="ARMOR_HEAD"),@VersionName(minVer=17, value="e")})
	NmsEnchantmentSlotType staticGetArmorHead();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="FISHING_ROD"),@VersionName(minVer=17, value="h")})
	NmsEnchantmentSlotType staticGetFishingRod();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="BREAKABLE"),@VersionName(minVer=17, value="j")})
	NmsEnchantmentSlotType staticGetBreakable();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="BOW"),@VersionName(minVer=17, value="k")})
	NmsEnchantmentSlotType staticGetBow();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="WEAPON"),@VersionName(minVer=17, value="l")})
	NmsEnchantmentSlotType staticGetWeapon();
}
