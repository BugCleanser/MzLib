package mz.mzlib.mc.packet.s2c.play;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.packet.Packet;

@DelegatorMinecraftClass(@VersionName(begin = 1900, name = "net.minecraft.network.packet.s2c.play.GameMessageS2CPacket"))
public interface PacketPlayGameMessageS2C extends Packet
{
}
