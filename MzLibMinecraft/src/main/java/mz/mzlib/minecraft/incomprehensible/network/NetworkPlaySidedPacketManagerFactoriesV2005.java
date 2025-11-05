package mz.mzlib.minecraft.incomprehensible.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.state.PlayStateFactories"))
public interface NetworkPlaySidedPacketManagerFactoriesV2005 extends WrapperObject
{
    WrapperFactory<NetworkPlaySidedPacketManagerFactoriesV2005> FACTORY = WrapperFactory.of(NetworkPlaySidedPacketManagerFactoriesV2005.class);
    @Deprecated
    @WrapperCreator
    static NetworkPlaySidedPacketManagerFactoriesV2005 create(Object wrapped)
    {
        return WrapperObject.create(NetworkPlaySidedPacketManagerFactoriesV2005.class, wrapped);
    }
    
    static NetworkPhaseSidedPacketManagerV2005.Factory c2sV_2105()
    {
        return FACTORY.getStatic().static$c2sV_2105();
    }
    @VersionRange(end=2105)
    @WrapMinecraftFieldAccessor(@VersionName(name="C2S"))
    NetworkPhaseSidedPacketManagerV2005.Factory static$c2sV_2105();
    static NetworkPhaseSidedPacketManagerV2005.FactoryWithContextV2105 c2sV2105()
    {
        return FACTORY.getStatic().static$c2sV2105();
    }
    @VersionRange(begin=2105)
    @WrapMinecraftFieldAccessor(@VersionName(name="C2S"))
    NetworkPhaseSidedPacketManagerV2005.FactoryWithContextV2105 static$c2sV2105();
    
    static NetworkPhaseSidedPacketManagerV2005.Factory s2c()
    {
        return FACTORY.getStatic().static$s2c();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="S2C"))
    NetworkPhaseSidedPacketManagerV2005.Factory static$s2c();
}
