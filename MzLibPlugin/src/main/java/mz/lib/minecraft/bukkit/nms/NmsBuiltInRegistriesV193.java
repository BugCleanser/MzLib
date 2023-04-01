package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.*;
import mz.lib.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass(@VersionalName(minVer=19.3f,value="net.minecraft.core.registries.BuiltInRegistries"))
public interface NmsBuiltInRegistriesV193 extends VersionalWrappedObject
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
	
	@VersionalWrappedFieldAccessor(@VersionalName("#3"))
	NmsRegistryBlocks staticGetEntityTypes();
	@VersionalWrappedFieldAccessor(@VersionalName("#4"))
	NmsRegistryBlocks staticGetItems();
	@VersionalWrappedFieldAccessor(@VersionalName("#2"))
	NmsIRegistry staticGetEnchants();
}
