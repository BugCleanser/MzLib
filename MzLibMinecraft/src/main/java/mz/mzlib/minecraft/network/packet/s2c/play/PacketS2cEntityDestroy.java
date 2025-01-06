package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket"))
public interface PacketS2cEntityDestroy extends Packet
{
    @WrapperCreator
    static PacketS2cEntityDestroy create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cEntityDestroy.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="entityIds"))
    List<Integer> getEntityIds();
}
