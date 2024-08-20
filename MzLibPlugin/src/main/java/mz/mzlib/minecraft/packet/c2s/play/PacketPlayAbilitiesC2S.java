package mz.mzlib.minecraft.packet.c2s.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.packet.Packet;

@WrapMinecraftClass(@VersionName(end = 1700, name = "net.minecraft.network.GameMessageS2CPacket"))
public interface PacketPlayAbilitiesC2S extends Packet
{
}