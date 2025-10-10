package mz.mzlib.minecraft.network.packet;

import io.netty.buffer.ByteBuf;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.util.PacketByteBuf", end=1600), @VersionName(name="net.minecraft.network.PacketByteBuf", begin=1600)})
public interface ByteBufPacket extends WrapperObject
{
    WrapperFactory<ByteBufPacket> FACTORY = WrapperFactory.of(ByteBufPacket.class);
    @Deprecated
    @WrapperCreator
    static ByteBufPacket create(Object wrapped)
    {
        return WrapperObject.create(ByteBufPacket.class, wrapped);
    }
    
    @Override
    ByteBuf getWrapped();
    
    static ByteBufPacket newInstance(ByteBuf delegate)
    {
        return create(null).staticNewInstance(delegate);
    }
    
    @WrapConstructor
    ByteBufPacket staticNewInstance(ByteBuf delegate);
    
    @WrapMinecraftMethod(@VersionName(name="readString"))
    String readString(int i);
    
    @WrapMinecraftMethod(@VersionName(name="writeString"))
    ByteBufPacket writeString(String str);
}
