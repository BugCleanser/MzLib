package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedMethod;

@WrappedBukkitClass({@VersionName(value="nms.PacketDataSerializer",maxVer=17),@VersionName(value="net.minecraft.network.PacketDataSerializer",minVer=17)})
public interface NmsPacketDataSerializer extends WrappedBukkitObject
{
	@WrappedMethod("e")
	String readString(int max);
}
