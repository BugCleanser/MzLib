package mz.mzlib.mc.packet.c2s.play;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.packet.Packet;

@DelegatorMinecraftClass(@VersionName(end = 1700, name = "net.minecraft.network.GameMessageS2CPacket"))
public interface PacketPlayAbilitiesC2S extends Packet
{
}