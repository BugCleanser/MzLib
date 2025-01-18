package mz.mzlib.minecraft.network.packet.codec;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.codec.PacketEncoder"))
public interface BinaryEncoderV2005 extends WrapperObject
{
    @WrapperCreator
    static BinaryEncoderV2005 create(Object wrapped)
    {
        return WrapperObject.create(BinaryEncoderV2005.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="encode"))
    void encode(Object buf, Object value);
}
