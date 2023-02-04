package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

import java.util.function.Function;

@WrappedBukkitClass({@VersionName(value="nms.EntityTypes",maxVer=17),@VersionName(value="net.minecraft.world.entity.EntityTypes",minVer=17)})
public interface NmsEntityTypes extends WrappedBukkitObject
{
	static NmsEntity spawn(NmsNBTTagCompound nbt,NmsWorld world)
	{
		if(BukkitWrapper.v17)
			return WrappedObject.getStatic(NmsEntityTypes.class).staticSpawnV17(nbt,world,e->e);
		else
			return WrappedObject.getStatic(NmsEntityTypes.class).staticSpawnV_17(nbt,world);
	}
	static String getNameV_13(NmsEntity entity)
	{
		return WrappedObject.getStatic(NmsEntityTypes.class).staticGetNameV_13(entity);
	}
	static String getTranslateKeyV_13(NmsEntity entity)
	{
		return "entity."+getNameV_13(entity)+".name";
	}
	static NmsRegistryMaterials getEntityTypesV_13()
	{
		return WrappedObject.getStatic(NmsEntityTypes.class).staticGetEntityTypesV_13();
	}
	@WrappedBukkitMethod(@VersionName(value="@0",maxVer=13))
	NmsRegistryMaterials staticGetEntityTypesV_13();
	@WrappedBukkitMethod(@VersionName(value="a",minVer=17))
	NmsEntity staticSpawnV17(NmsNBTTagCompound nbt,NmsWorld world,Function<Object,Object> entityProcessor);
	@WrappedBukkitMethod(@VersionName(value="a",maxVer=17))
	NmsEntity staticSpawnV_17(NmsNBTTagCompound nbt,NmsWorld world);
	@WrappedBukkitMethod(@VersionName(value="b",maxVer=13))
	String staticGetNameV_13(NmsEntity entity);
	@WrappedBukkitMethod(@VersionName(value="g",minVer=17))
	String getTranslateKeyV17();
	@WrappedBukkitMethod({@VersionName(value="d",minVer=13,maxVer=14),@VersionName(value="f",minVer=14,maxVer=17)})
	String getTranslateKeyV13_17();
	default String getTranslateKeyV13()
	{
		if(BukkitWrapper.v17)
			return getTranslateKeyV17();
		else
			return getTranslateKeyV13_17();
	}
}
