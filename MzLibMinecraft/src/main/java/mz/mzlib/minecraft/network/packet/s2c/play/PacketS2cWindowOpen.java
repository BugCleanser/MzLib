package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.window.ModuleWindow;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name="net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket", end=1400),
                @VersionName(name="net.minecraft.network.packet.s2c.play.OpenContainerS2CPacket", begin=1400, end=1600),
                @VersionName(name="net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket", begin=1600)
        })
public interface PacketS2cWindowOpen extends WrapperObject, Packet
{
    @WrapperCreator
    static PacketS2cWindowOpen create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cWindowOpen.class, wrapped);
    }
    
    @VersionRange(end=1400)
    @WrapConstructor
    ModuleWindow.NothingPacketS2cWindowOpen staticNewInstanceV_1400(int syncId, String typeId, Text title, int size);
}
