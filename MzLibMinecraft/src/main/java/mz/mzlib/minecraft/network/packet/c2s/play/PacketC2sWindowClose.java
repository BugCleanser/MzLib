package mz.mzlib.minecraft.network.packet.c2s.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;


@WrapMinecraftClass({@VersionName(name="net.minecraft.network.packet.c2s.play.GuiCloseC2SPacket", end=1604), @VersionName(name="net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket", begin=1604)})
public interface PacketC2sWindowClose extends Packet
{
    @WrapperCreator
    static PacketC2sWindowClose create(Object wrapped)
    {
        return WrapperObject.create(PacketC2sWindowClose.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="id", end=1604), @VersionName(name="syncId", begin=1604)})
    int getSyncId();
    
    static PacketC2sWindowClose newInstance(int syncId)
    {
        return create(null).staticNewInstance(syncId);
    }
    
    @WrapConstructor
    PacketC2sWindowClose staticNewInstance(int syncId);
}
