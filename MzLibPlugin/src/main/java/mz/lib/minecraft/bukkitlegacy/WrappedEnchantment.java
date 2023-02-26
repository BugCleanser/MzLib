package mz.lib.minecraft.bukkitlegacy;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedClass;
import mz.lib.wrapper.WrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.*;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.Map;

@WrappedClass("org.bukkit.enchantments.Enchantment")
public interface WrappedEnchantment extends VersionalWrappedObject
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
	@VersionalWrappedFieldAccessor(@VersionalName(maxVer=13, value="byId"))
	Map<Integer,Enchantment> staticGetByIdV_13();
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=13, value="byKey"))
	Map<NamespacedKey,Enchantment> staticGetByKeyV13();
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=13, value="key"))
	NamespacedKey getKeyV13();
}
