package mz.mzlib.minecraft.bukkit.network;

import io.netty.util.concurrent.GenericFutureListener;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.item.CraftItemStack;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketCallbacksV1901;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapSameClass(ClientConnection.class)
public interface ClientConnectionBukkit extends ClientConnection
{
    WrapperFactory<CraftItemStack> FACTORY = WrapperFactory.of(CraftItemStack.class);
    @Deprecated
    @WrapperCreator
    static ClientConnectionBukkit create(Object object)
    {
        return WrapperObject.create(ClientConnectionBukkit.class, object);
    }

    @VersionRange(begin = 1701, end = 1901)
    @WrapMinecraftMethod(@VersionName(name = "writePacket"))
    void sendPacketImmediatelyV1701_1901(Packet packet, GenericFutureListener<?> callbacks, Boolean flush);

    @VersionRange(begin = 1901, end = 2002)
    @WrapMinecraftMethod(@VersionName(name = "sendPacket"))
    void sendPacketImmediatelyV1901_2002(Packet packet, PacketCallbacksV1901 callbacks, Boolean flush);
}
