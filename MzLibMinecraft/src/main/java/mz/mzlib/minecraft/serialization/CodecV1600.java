package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.serialization.Codec", begin=1600))
public interface CodecV1600 extends WrapperObject, EncoderV1600, DecoderV1600
{
    @WrapperCreator
    static CodecV1600 create(Object wrapped)
    {
        return WrapperObject.create(CodecV1600.class, wrapped);
    }
}
