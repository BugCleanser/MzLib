package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;

@WrappedBukkitClass({@VersionName(value="nms.IProjectile",maxVer=17),@VersionName(value="net.minecraft.world.entity.projectile.IProjectile",minVer=17)})
public interface NmsIProjectile extends NmsEntity
{
}
