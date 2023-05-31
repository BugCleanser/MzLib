package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass(@VersionName(minVer=19.3f,value="net.minecraft.core.registries.BuiltInRegistries"))
public interface NmsBuiltInRegistriesV193 extends WrappedBukkitObject
{
	static NmsRegistryBlocks getEntityTypes()
	{
		return WrappedObject.getStatic(NmsBuiltInRegistriesV193.class).staticGetEntityTypes();
	}
	static NmsRegistryBlocks getItems()
	{
		return WrappedObject.getStatic(NmsBuiltInRegistriesV193.class).staticGetItems();
	}
	static NmsIRegistry getEnchants()
	{
		return WrappedObject.getStatic(NmsBuiltInRegistriesV193.class).staticGetEnchants();
	}
	
	@WrappedBukkitFieldAccessor(@VersionName("#3"))
	NmsRegistryBlocks staticGetEntityTypes();
	@WrappedBukkitFieldAccessor(@VersionName("#4"))
	NmsRegistryBlocks staticGetItems();
	@WrappedBukkitFieldAccessor(@VersionName("#2"))
	NmsIRegistry staticGetEnchants();
}
