package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.serialization.CodecV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.TextCodecs", begin=2003))
public interface TextCodecV2003 extends WrapperObject
{
    @WrapperCreator
    static TextCodecV2003 create(Object wrapped)
    {
        return WrapperObject.create(TextCodecV2003.class, wrapped);
    }
    
    static CodecV1600 codec()
    {
        return create(null).staticCodec();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="CODEC"))
    CodecV1600 staticCodec();
}
