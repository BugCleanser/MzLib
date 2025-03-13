package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.serialization.Codec", begin=1600))
public interface CodecV1600 extends WrapperObject, EncoderV1600, DecoderV1600
{
    WrapperFactory<CodecV1600> FACTORY = WrapperFactory.find(CodecV1600.class);
    @Deprecated
    @WrapperCreator
    static CodecV1600 create(Object wrapped)
    {
        return WrapperObject.create(CodecV1600.class, wrapped);
    }
}
