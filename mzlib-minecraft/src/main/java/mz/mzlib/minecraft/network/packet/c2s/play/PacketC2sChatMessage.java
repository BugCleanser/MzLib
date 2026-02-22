package mz.mzlib.minecraft.network.packet.c2s.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket"))
public interface PacketC2sChatMessage extends Packet
{
    WrapperFactory<PacketC2sChatMessage> FACTORY = WrapperFactory.of(PacketC2sChatMessage.class);
    @WrapMinecraftFieldAccessor(@VersionName(name = "chatMessage"))
    String getMessage();

    @WrapMinecraftFieldAccessor(@VersionName(name = "chatMessage"))
    void setMessage(String value);
}

