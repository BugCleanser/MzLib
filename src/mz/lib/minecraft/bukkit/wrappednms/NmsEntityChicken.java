package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;

@WrappedBukkitClass({@VersionName(value="nms.EntityChicken",maxVer=17),@VersionName(value="net.minecraft.world.entity.animal.EntityChicken",minVer=17)})
public interface NmsEntityChicken extends NmsEntityLiving
{
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=18, value="bZ"),@VersionName(minVer=18, value="cc")})
	int getEggLayTime();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=18, value="bZ"),@VersionName(minVer=18, value="cc")})
	NmsEntityChicken setEggLayTime(int time);
	@WrappedBukkitMethod({@VersionName(maxVer=18, value="isChickenJockey"),@VersionName(minVer=18, value="t")})
	boolean isChickenJockey();
}
