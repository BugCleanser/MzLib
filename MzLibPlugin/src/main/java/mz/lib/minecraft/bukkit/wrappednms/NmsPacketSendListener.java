package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass(@VersionName(value="net.minecraft.network.PacketSendListener",minVer=19.1f))
public interface NmsPacketSendListener extends WrappedBukkitObject
{
}
