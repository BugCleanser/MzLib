package mz.lib.minecraft.bukkit;

import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedClass;
import mz.lib.wrapper.WrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.Map;

@WrappedClass("org.bukkit.enchantments.Enchantment")
public interface WrappedEnchantment extends WrappedBukkitObject
{
	static Map<String,Enchantment> getByName()
	{
		return WrappedObject.getStatic(WrappedEnchantment.class).staticGetByName();
	}
	static Map<Integer,Enchantment> getByIdV_13()
	{
		return WrappedObject.getStatic(WrappedEnchantment.class).staticGetByIdV_13();
	}
	static Map<NamespacedKey,Enchantment> getByKeyV13()
	{
		return WrappedObject.getStatic(WrappedEnchantment.class).staticGetByKeyV13();
	}
	@WrappedFieldAccessor("byName")
	Map<String,Enchantment> staticGetByName();
	@WrappedBukkitFieldAccessor(@VersionName(maxVer=13, value="byId"))
	Map<Integer,Enchantment> staticGetByIdV_13();
	@WrappedBukkitFieldAccessor(@VersionName(minVer=13, value="byKey"))
	Map<NamespacedKey,Enchantment> staticGetByKeyV13();
	@WrappedBukkitFieldAccessor(@VersionName(minVer=13, value="key"))
	NamespacedKey getKeyV13();
}
