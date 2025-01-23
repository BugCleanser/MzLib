package mz.mzlib.minecraft.network.packet;

import io.netty.buffer.ByteBuf;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.util.PacketByteBuf", end=1600), @VersionName(name="net.minecraft.network.PacketByteBuf", begin=1600)})
public interface ByteBufPacket extends WrapperObject
{
    @WrapperCreator
    static ByteBufPacket create(Object wrapped)
    {
        return WrapperObject.create(ByteBufPacket.class, wrapped);
    }
    
    static ByteBufPacket newInstance(ByteBuf delegate)
    {
        return create(null).staticNewInstance(delegate);
    }
    @WrapConstructor
    ByteBufPacket staticNewInstance(ByteBuf delegate);
}
