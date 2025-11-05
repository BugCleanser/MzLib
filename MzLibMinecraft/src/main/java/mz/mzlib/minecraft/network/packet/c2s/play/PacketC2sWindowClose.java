package mz.mzlib.minecraft.network.packet.c2s.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.*;


@WrapMinecraftClass({@VersionName(name="net.minecraft.network.packet.c2s.play.GuiCloseC2SPacket", end=1604), @VersionName(name="net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket", begin=1604)})
public interface PacketC2sWindowClose extends Packet
{
    WrapperFactory<PacketC2sWindowClose> FACTORY = WrapperFactory.of(PacketC2sWindowClose.class);
    @Deprecated
    @WrapperCreator
    static PacketC2sWindowClose create(Object wrapped)
    {
        return WrapperObject.create(PacketC2sWindowClose.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="id", end=1604), @VersionName(name="syncId", begin=1604)})
    int getSyncId();
    @WrapMinecraftFieldAccessor({@VersionName(name="id", end=1604), @VersionName(name="syncId", begin=1604)})
    void setSyncId(int value);
    
    static PacketC2sWindowClose newInstance(int syncId)
    {
        return FACTORY.getStatic().staticNewInstance(syncId);
    }
    PacketC2sWindowClose staticNewInstance(int syncId);
    @VersionRange(end=1700)
    @WrapConstructor
    PacketC2sWindowClose staticNewInstanceV_1700();
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1700)
    default PacketC2sWindowClose staticNewInstanceV_1700(int syncId)
    {
        PacketC2sWindowClose result = this.staticNewInstanceV_1700();
        result.setSyncId(syncId);
        return result;
    }
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1700)
    @WrapConstructor
    PacketC2sWindowClose staticNewInstanceV1700(int syncId);
}
