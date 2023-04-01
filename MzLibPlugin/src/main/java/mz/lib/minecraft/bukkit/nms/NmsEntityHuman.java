package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.obc.ObcEntity;
import mz.lib.*;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;
import org.bukkit.entity.HumanEntity;

@VersionalWrappedClass({@VersionalName(value="nms.EntityHuman",maxVer=17),@VersionalName(value="net.minecraft.world.entity.player.EntityHuman",minVer=17)})
public interface NmsEntityHuman extends NmsEntityLiving
{
	static NmsEntityHuman fromBukkit(HumanEntity player)
	{
		return WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityHuman.class);
	}
	@VersionalWrappedFieldAccessor(@VersionalName(value="defaultContainer",maxVer=14))
	NmsContainer getPlayerContainerV_14();
	@VersionalWrappedFieldAccessor({@VersionalName(value="defaultContainer",minVer=14),@VersionalName(minVer=17,value="@0")})
	NmsContainerPlayer getPlayerContainerV14();
	default NmsContainer getPlayerContainer()
	{
		if(Server.instance.version>=14)
			return getPlayerContainerV14();
		else
			return getPlayerContainerV_14();
	}
	@VersionalWrappedFieldAccessor({@VersionalName("activeContainer"),@VersionalName(value="@1",maxVer=14),@VersionalName(value="@0",minVer=14)})
	NmsContainer getOpenContainer();
	
	@VersionalWrappedMethod({@VersionalName("drop"),@VersionalName(minVer=18,value="a")})
	NmsEntityItem drop(NmsItemStack item,boolean setThrower);
}
