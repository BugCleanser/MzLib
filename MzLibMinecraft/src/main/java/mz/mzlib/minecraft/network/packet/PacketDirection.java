package mz.mzlib.minecraft.network.packet;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.NetworkSide"))
public interface PacketDirection extends WrapperObject
{
    @WrapperCreator
    static PacketDirection create(Object wrapped)
    {
        return WrapperObject.create(PacketDirection.class, wrapped);
    }
    
    static PacketDirection c2s()
    {
        return create(null).staticC2s();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="SERVERBOUND", end=1400), @VersionName(name="field_11941", begin=1400)})
    PacketDirection staticC2s();
    
    static PacketDirection s2c()
    {
        return create(null).staticS2c();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="CLIENTBOUND", end=1400), @VersionName(name="field_11942", begin=1400)})
    PacketDirection staticS2c();
}
