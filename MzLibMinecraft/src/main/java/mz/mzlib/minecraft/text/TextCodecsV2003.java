package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.serialization.CodecV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2003)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.TextCodecs"))
public interface TextCodecsV2003 extends WrapperObject
{
    WrapperFactory<TextCodecsV2003> FACTORY = WrapperFactory.of(TextCodecsV2003.class);
    
    static CodecV1600 codec()
    {
        return FACTORY.getStatic().staticCodec();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="CODEC"))
    CodecV1600 staticCodec();
}
