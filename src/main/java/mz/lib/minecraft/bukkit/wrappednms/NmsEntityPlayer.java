package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

@WrappedBukkitClass({@VersionName(value="nms.EntityPlayer",maxVer=17),@VersionName(value="net.minecraft.server.level.EntityPlayer",minVer=17)})
public interface NmsEntityPlayer extends NmsEntityHuman
{
	@WrappedBukkitFieldAccessor({@VersionName("playerConnection"),@VersionName(minVer=17, value="b")})
	NmsPlayerConnection getPlayerConnection();
}
