package mz.mzlib.minecraft.incomprehensible.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.state.ConfigurationStates"))
public interface NetworkConfigurationSidedPacketManagersV2005 extends WrapperObject
{
    WrapperFactory<NetworkConfigurationSidedPacketManagersV2005> FACTORY = WrapperFactory.find(NetworkConfigurationSidedPacketManagersV2005.class);
    @Deprecated
    @WrapperCreator
    static NetworkConfigurationSidedPacketManagersV2005 create(Object wrapped)
    {
        return WrapperObject.create(NetworkConfigurationSidedPacketManagersV2005.class, wrapped);
    }
    
    static NetworkPhaseSidedPacketManagerV2005 c2s()
    {
        return create(null).staticC2s();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="C2S"))
    NetworkPhaseSidedPacketManagerV2005 staticC2s();
    
    static NetworkPhaseSidedPacketManagerV2005 s2c()
    {
        return create(null).staticS2c();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="S2C"))
    NetworkPhaseSidedPacketManagerV2005 staticS2c();
}
