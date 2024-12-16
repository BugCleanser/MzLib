package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.network.packet.Packet;

@WrapMinecraftClass(@VersionName(begin = 1900, name = "net.minecraft.network.packet.s2c.play.GameMessageS2CPacket"))
public interface PacketS2cGameMessage extends Packet
{
}
