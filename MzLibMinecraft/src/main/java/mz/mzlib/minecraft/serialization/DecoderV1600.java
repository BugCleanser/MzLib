package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.serialization.Decoder", begin = 1600))
public interface DecoderV1600 extends WrapperObject
{
    WrapperFactory<DecoderV1600> FACTORY = WrapperFactory.find(DecoderV1600.class);
    @Deprecated
    @WrapperCreator
    static DecoderV1600 create(Object wrapped)
    {
        return WrapperObject.create(DecoderV1600.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="parse"))
    DataResultV1600 parse(DynamicOpsV1300 ops, Object data);
}
