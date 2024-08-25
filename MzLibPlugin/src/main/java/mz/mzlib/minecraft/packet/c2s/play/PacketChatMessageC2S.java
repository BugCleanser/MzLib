package mz.mzlib.minecraft.packet.c2s.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket", end=1400),
                @VersionName(name = "net.minecraft.server.network.packet.ChatMessageC2SPacket", begin=1400, end=1502),
                @VersionName(name = "net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket", begin=1502)
        })
public interface PacketChatMessageC2S extends Packet
{
    @WrapperCreator
    static PacketChatMessageC2S create(Object wrapped)
    {
        return WrapperObject.create(PacketChatMessageC2S.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="chatMessage"))
    String getChatMessage();
    @WrapMinecraftFieldAccessor(@VersionName(name="chatMessage"))
    void setChatMessage(String value);
}
