package mz.mzlib.minecraft.network.packet.codec;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2005)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.network.codec.PacketDecoder"))
public interface BinaryDecoderV2005 extends WrapperObject
{
    WrapperFactory<BinaryDecoderV2005> FACTORY = WrapperFactory.of(BinaryDecoderV2005.class);
    @Deprecated
    @WrapperCreator
    static BinaryDecoderV2005 create(Object wrapped)
    {
        return WrapperObject.create(BinaryDecoderV2005.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name = "decode"))
    Object decode(Object buf);
}
