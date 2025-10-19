package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2109)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.ObjectTextContent"))
public interface TextContentObjectV2109 extends WrapperObject, TextContentV1900
{
    WrapperFactory<TextContentObjectV2109> FACTORY = WrapperFactory.of(TextContentObjectV2109.class);
    
    // TODO
}
