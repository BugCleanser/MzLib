package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.IProjectile",maxVer=17),@VersionalName(value="net.minecraft.world.entity.projectile.IProjectile",minVer=17)})
public interface NmsIProjectile extends NmsEntity
{
}
