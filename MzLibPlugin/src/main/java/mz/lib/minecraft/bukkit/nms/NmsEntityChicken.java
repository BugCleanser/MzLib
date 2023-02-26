package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.EntityChicken",maxVer=17),@VersionalName(value="net.minecraft.world.entity.animal.EntityChicken",minVer=17)})
public interface NmsEntityChicken extends NmsEntityLiving
{
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=18, value="bZ"),@VersionalName(minVer=18, value="cc")})
	int getEggLayTime();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=18, value="bZ"),@VersionalName(minVer=18, value="cc")})
	NmsEntityChicken setEggLayTime(int time);
	@VersionalWrappedMethod({@VersionalName(maxVer=18, value="isChickenJockey"),@VersionalName(minVer=18, value="t")})
	boolean isChickenJockey();
}
