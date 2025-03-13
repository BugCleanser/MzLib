package mz.mzlib.minecraft.incomprehensible.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.state.HandshakeStates"))
public interface NetworkHandshakeSidedPacketManagersV2005 extends WrapperObject
{
    WrapperFactory<NetworkHandshakeSidedPacketManagersV2005> FACTORY = WrapperFactory.find(NetworkHandshakeSidedPacketManagersV2005.class);
    @Deprecated
    @WrapperCreator
    static NetworkHandshakeSidedPacketManagersV2005 create(Object wrapped)
    {
        return WrapperObject.create(NetworkHandshakeSidedPacketManagersV2005.class, wrapped);
    }
    
    static NetworkPhaseSidedPacketManagerV2005 c2s()
    {
        return create(null).staticC2s();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="C2S"))
    NetworkPhaseSidedPacketManagerV2005 staticC2s();
}
