package mz.mzlib.minecraft.incomprehensible.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.state.PlayStateFactories"))
public interface NetworkPlaySidedPacketManagerFactoriesV2005 extends WrapperObject
{
    @WrapperCreator
    static NetworkPlaySidedPacketManagerFactoriesV2005 create(Object wrapped)
    {
        return WrapperObject.create(NetworkPlaySidedPacketManagerFactoriesV2005.class, wrapped);
    }
    
    static NetworkPhaseSidedPacketManagerV2005.Factory c2s()
    {
        return create(null).staticC2s();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="C2S"))
    NetworkPhaseSidedPacketManagerV2005.Factory staticC2s();
    
    static NetworkPhaseSidedPacketManagerV2005.Factory s2c()
    {
        return create(null).staticS2c();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="S2C"))
    NetworkPhaseSidedPacketManagerV2005.Factory staticS2c();
}
