package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.serialization.CodecV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.TextCodecs", begin=2003))
public interface TextCodecV2003 extends WrapperObject
{
    WrapperFactory<TextCodecV2003> FACTORY = WrapperFactory.of(TextCodecV2003.class);
    @Deprecated
    @WrapperCreator
    static TextCodecV2003 create(Object wrapped)
    {
        return WrapperObject.create(TextCodecV2003.class, wrapped);
    }
    
    static CodecV1600.Wrapper<Text> codec()
    {
        return new CodecV1600.Wrapper<>(codec0(), Text.FACTORY);
    }
    static CodecV1600<?> codec0()
    {
        return create(null).staticCodec0();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="CODEC"))
    CodecV1600<?> staticCodec0();
}
