package mz.mzlib.minecraft.network.packet.c2s.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.c2s.play.RenameItemC2SPacket", begin=1300))
public interface PacketC2sWindowAnvilNameV1300 extends Packet
{
    @WrapperCreator
    static PacketC2sWindowAnvilNameV1300 create(Object wrapped)
    {
        return WrapperObject.create(PacketC2sWindowAnvilNameV1300.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    String getName();
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    void setName(String value);
    
    static PacketC2sWindowAnvilNameV1300 newInstance(String name)
    {
        return create(null).staticNewInstance(name);
    }
    @WrapConstructor
    PacketC2sWindowAnvilNameV1300 staticNewInstance(String name);
}
