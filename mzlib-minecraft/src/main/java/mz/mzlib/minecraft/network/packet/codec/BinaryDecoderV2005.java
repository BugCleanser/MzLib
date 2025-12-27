package mz.mzlib.minecraft.network.packet.codec;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2005)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.network.codec.PacketDecoder"))
public interface BinaryDecoderV2005<B, V> extends WrapperObject
{
    WrapperFactory<BinaryDecoderV2005<?, ?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(BinaryDecoderV2005.class));

    @WrapMinecraftMethod(@VersionName(name = "decode"))
    V decode(B buf);
}
