package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;

@VersionalWrappedClass({@VersionalName(value="nms.EntityPlayer",maxVer=17),@VersionalName(value="net.minecraft.server.level.EntityPlayer",minVer=17)})
public interface NmsEntityPlayer extends NmsEntityHuman
{
	@VersionalWrappedFieldAccessor({@VersionalName("playerConnection"),@VersionalName(minVer=17, value="b")})
	NmsPlayerConnection getPlayerConnection();
}
