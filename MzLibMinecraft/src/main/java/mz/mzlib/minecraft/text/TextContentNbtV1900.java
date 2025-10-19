package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1900)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.NbtTextContent"))
public interface TextContentNbtV1900 extends WrapperObject, TextContentV1900
{
    WrapperFactory<TextContentNbtV1900> FACTORY = WrapperFactory.of(TextContentNbtV1900.class);
    
    // TODO
}
