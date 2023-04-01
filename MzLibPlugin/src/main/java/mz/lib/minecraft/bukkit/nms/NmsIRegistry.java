package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import mz.lib.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.IRegistry",maxVer=17),@VersionalName(value="net.minecraft.core.IRegistry",minVer=17)})
public interface NmsIRegistry extends VersionalWrappedObject
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
		if(Server.instance.version<19.3f)
			return WrappedObject.getStatic(NmsIRegistry.class).staticGetEnchantsV13_193();
		else
			return NmsBuiltInRegistriesV193.getEnchants();
	}
	
	static NmsRegistryMaterials getEntityTypesV13()
	{
		if(Server.instance.version<14)
			return WrappedObject.getStatic(NmsIRegistry.class).staticGetEntityTypesV13_14().cast(NmsRegistryMaterials.class);
		else if(Server.instance.version<19.3f)
			return WrappedObject.getStatic(NmsIRegistry.class).staticGetEntityTypesV14_193();
		else
			return NmsBuiltInRegistriesV193.getEntityTypes();
	}
	@VersionalWrappedFieldAccessor(@VersionalName(value={"ENTITY_TYPE","#12"},minVer=13,maxVer=14))
	NmsIRegistry staticGetEntityTypesV13_14();
	@VersionalWrappedFieldAccessor(@VersionalName(value={"ENTITY_TYPE","#3"},minVer=14,maxVer=19.3f))
	NmsRegistryBlocks staticGetEntityTypesV14_193();
	
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=13,value="ITEM",maxVer=15))
	NmsIRegistry staticGetItemsV13_15();
	@VersionalWrappedFieldAccessor({@VersionalName(minVer=15,value="ITEM",maxVer=19.3f),@VersionalName(minVer=17,value="#4",maxVer=19.3f)})
	NmsRegistryBlocks staticGetItemsV15_193();
	
	default NmsIRegistry staticGetItemsV13()
	{
		if(Server.instance.version<15)
			return staticGetItemsV13_15();
		else if(Server.instance.version<19.3f)
			return staticGetItemsV15_193().cast(NmsIRegistry.class);
		else
			return NmsBuiltInRegistriesV193.getItems().cast(NmsIRegistry.class);
	}
	
	@VersionalWrappedFieldAccessor({@VersionalName(minVer=13,maxVer=19.3f,value="ENCHANTMENT"),@VersionalName(minVer=17, maxVer=18, value="X"),@VersionalName(minVer=18,maxVer=18.2f, value="Y"),@VersionalName(minVer=18.2f, value="V",maxVer=19),@VersionalName(value="W",minVer=19,maxVer=19.3f)})
	NmsIRegistry staticGetEnchantsV13_193();
	@WrappedMethod({"getKey","b"})
	NmsMinecraftKey getKey(WrappedObject object);
	
	@WrappedMethod({"get","a"})
	WrappedObject get(NmsMinecraftKey key);
}
