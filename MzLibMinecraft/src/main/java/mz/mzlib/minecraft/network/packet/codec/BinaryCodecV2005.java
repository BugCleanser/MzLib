package mz.mzlib.minecraft.network.packet.codec;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.codec.PacketCodec"))
public interface BinaryCodecV2005 extends WrapperObject, BinaryDecoderV2005, BinaryEncoderV2005
{
    WrapperFactory<BinaryCodecV2005> FACTORY = WrapperFactory.find(BinaryCodecV2005.class);
    @Deprecated
    @WrapperCreator
    static BinaryCodecV2005 create(Object wrapped)
    {
        return WrapperObject.create(BinaryCodecV2005.class, wrapped);
    }
}
