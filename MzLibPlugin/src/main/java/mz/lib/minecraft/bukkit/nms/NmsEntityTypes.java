package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;

import java.util.function.Function;

@VersionalWrappedClass({@VersionalName(value="nms.EntityTypes",maxVer=17),@VersionalName(value="net.minecraft.world.entity.EntityTypes",minVer=17)})
public interface NmsEntityTypes extends VersionalWrappedObject
{
	static NmsEntity spawn(NmsNBTTagCompound nbt,NmsWorld world)
	{
		if(Server.instance.v17)
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
	@VersionalWrappedMethod(@VersionalName(value="@0",maxVer=13))
	NmsRegistryMaterials staticGetEntityTypesV_13();
	@VersionalWrappedMethod(@VersionalName(value="a",minVer=17))
	NmsEntity staticSpawnV17(NmsNBTTagCompound nbt,NmsWorld world,Function<Object,Object> entityProcessor);
	@VersionalWrappedMethod(@VersionalName(value="a",maxVer=17))
	NmsEntity staticSpawnV_17(NmsNBTTagCompound nbt,NmsWorld world);
	@VersionalWrappedMethod(@VersionalName(value="b",maxVer=13))
	String staticGetNameV_13(NmsEntity entity);
	@VersionalWrappedMethod(@VersionalName(value="g",minVer=17))
	String getTranslateKeyV17();
	@VersionalWrappedMethod({@VersionalName(value="d",minVer=13,maxVer=14),@VersionalName(value="f",minVer=14,maxVer=17)})
	String getTranslateKeyV13_17();
	default String getTranslateKeyV13()
	{
		if(Server.instance.v17)
			return getTranslateKeyV17();
		else
			return getTranslateKeyV13_17();
	}
}
