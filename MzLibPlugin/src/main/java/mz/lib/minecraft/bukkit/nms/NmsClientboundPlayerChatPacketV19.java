package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass(@VersionalName(value="net.minecraft.network.protocol.game.ClientboundPlayerChatPacket",minVer=19))
public interface NmsClientboundPlayerChatPacketV19 extends NmsPacket
{
}
