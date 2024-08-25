package mz.mzlib.minecraft.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.packet.Packet;

@WrapMinecraftClass(@VersionName(begin = 1900, name = "net.minecraft.network.packet.s2c.play.GameMessageS2CPacket"))
public interface PacketGameMessageS2C extends Packet
{
}
