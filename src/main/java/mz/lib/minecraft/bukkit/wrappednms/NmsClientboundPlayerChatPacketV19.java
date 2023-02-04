package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;

@WrappedBukkitClass(@VersionName(value="net.minecraft.network.protocol.game.ClientboundPlayerChatPacket",minVer=19))
public interface NmsClientboundPlayerChatPacketV19 extends NmsPacket
{
}
