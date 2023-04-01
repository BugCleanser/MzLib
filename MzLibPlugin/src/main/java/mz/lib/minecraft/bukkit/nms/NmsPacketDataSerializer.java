package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.PacketDataSerializer",maxVer=17),@VersionalName(value="net.minecraft.network.PacketDataSerializer",minVer=17)})
public interface NmsPacketDataSerializer extends VersionalWrappedObject
{
	@WrappedMethod("e")
	String readString(int max);
}
