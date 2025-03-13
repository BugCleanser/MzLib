package mz.mzlib.minecraft.network.packet.c2s.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket"))
public interface PacketC2sChatMessage extends Packet
{
    WrapperFactory<PacketC2sChatMessage> FACTORY = WrapperFactory.find(PacketC2sChatMessage.class);
    @Deprecated
    @WrapperCreator
    static PacketC2sChatMessage create(Object wrapped)
    {
        return WrapperObject.create(PacketC2sChatMessage.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="chatMessage"))
    String getMessage();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="chatMessage"))
    void setMessage(String value);
}
