package mz.mzlib.minecraft.incomprehensible.network;

import io.netty.buffer.ByteBuf;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.codec.BinaryCodecV2005;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Function;

@VersionRange(begin = 2005)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.network.NetworkState", end = 2105),
    @VersionName(name = "net.minecraft.network.state.NetworkState", begin = 2105)
})
public interface NetworkPhaseSidedPacketManagerV2005 extends WrapperObject
{
    WrapperFactory<NetworkPhaseSidedPacketManagerV2005> FACTORY = WrapperFactory.of(
        NetworkPhaseSidedPacketManagerV2005.class);
    @Deprecated
    @WrapperCreator
    static NetworkPhaseSidedPacketManagerV2005 create(Object wrapped)
    {
        return WrapperObject.create(NetworkPhaseSidedPacketManagerV2005.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name = "comp_2236"))
    BinaryCodecV2005 getCodec();

    @VersionRange(begin = 2005)
    @WrapMinecraftClass({
        @VersionName(name = "net.minecraft.network.NetworkState$Factory", end = 2105),
        @VersionName(name = "net.minecraft.network.state.NetworkStateFactory", begin = 2105)
    })
    interface Factory extends WrapperObject
    {
        WrapperFactory<Factory> FACTORY = WrapperFactory.of(Factory.class);
        @Deprecated
        @WrapperCreator
        static Factory create(Object wrapped)
        {
            return WrapperObject.create(Factory.class, wrapped);
        }

        @WrapMinecraftMethod(@VersionName(name = "bind"))
        NetworkPhaseSidedPacketManagerV2005 make(Function<ByteBuf, ? extends ByteBuf> function);
    }

    @VersionRange(begin = 2105)
    @WrapMinecraftClass(@VersionName(name = "net.minecraft.network.state.ContextAwareNetworkStateFactory"))
    interface FactoryWithContextV2105 extends WrapperObject
    {
        @WrapMinecraftMethod(@VersionName(name = "bind"))
        NetworkPhaseSidedPacketManagerV2005 make(Function<ByteBuf, ? extends ByteBuf> function, Object context);
    }
}
