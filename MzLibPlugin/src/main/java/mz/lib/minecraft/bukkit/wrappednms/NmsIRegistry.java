package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.IRegistry",maxVer=17),@VersionName(value="net.minecraft.core.IRegistry",minVer=17)})
public interface NmsIRegistry extends WrappedBukkitObject
{
	static NmsRegistryMaterials getItemsV13_14()
	{
		return WrappedObject.getStatic(NmsIRegistry.class).staticGetItemsV13().cast(NmsRegistryMaterials.class);
	}
	static NmsRegistryBlocks getItemsV14()
	{
		return WrappedObject.getStatic(NmsIRegistry.class).staticGetItemsV13().cast(NmsRegistryBlocks.class);
	}
	static NmsIRegistry getEnchantsV13()
	{
		if(BukkitWrapper.version<19.3f)
			return WrappedObject.getStatic(NmsIRegistry.class).staticGetEnchantsV13_193();
		else
			return NmsBuiltInRegistriesV193.getEnchants();
	}
	
	static NmsRegistryMaterials getEntityTypesV13()
	{
		if(BukkitWrapper.version<14)
			return WrappedObject.getStatic(NmsIRegistry.class).staticGetEntityTypesV13_14().cast(NmsRegistryMaterials.class);
		else if(BukkitWrapper.version<19.3f)
			return WrappedObject.getStatic(NmsIRegistry.class).staticGetEntityTypesV14_193();
		else
			return NmsBuiltInRegistriesV193.getEntityTypes();
	}
	@WrappedBukkitFieldAccessor(@VersionName(value={"ENTITY_TYPE","#12"},minVer=13,maxVer=14))
	NmsIRegistry staticGetEntityTypesV13_14();
	@WrappedBukkitFieldAccessor(@VersionName(value={"ENTITY_TYPE","#3"},minVer=14,maxVer=19.3f))
	NmsRegistryBlocks staticGetEntityTypesV14_193();
	
	@WrappedBukkitFieldAccessor(@VersionName(minVer=13,value="ITEM",maxVer=15))
	NmsIRegistry staticGetItemsV13_15();
	@WrappedBukkitFieldAccessor({@VersionName(minVer=15,value="ITEM",maxVer=19.3f),@VersionName(minVer=17,value="#4",maxVer=19.3f)})
	NmsRegistryBlocks staticGetItemsV15_193();
	
	default NmsIRegistry staticGetItemsV13()
	{
		if(BukkitWrapper.version<15)
			return staticGetItemsV13_15();
		else if(BukkitWrapper.version<19.3f)
			return staticGetItemsV15_193().cast(NmsIRegistry.class);
		else
			return NmsBuiltInRegistriesV193.getItems().cast(NmsIRegistry.class);
	}
	
	@WrappedBukkitFieldAccessor({@VersionName(minVer=13,maxVer=19.3f,value="ENCHANTMENT"),@VersionName(minVer=17, maxVer=18, value="X"),@VersionName(minVer=18,maxVer=18.2f, value="Y"),@VersionName(minVer=18.2f, value="V",maxVer=19),@VersionName(value="W",minVer=19,maxVer=19.3f)})
	NmsIRegistry staticGetEnchantsV13_193();
	@WrappedMethod({"getKey","b"})
	NmsMinecraftKey getKey(WrappedObject object);
	
	@WrappedMethod({"get","a"})
	WrappedObject get(NmsMinecraftKey key);
}
