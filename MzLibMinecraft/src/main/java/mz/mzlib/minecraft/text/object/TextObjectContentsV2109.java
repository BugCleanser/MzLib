package mz.mzlib.minecraft.text.object;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2109)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.object.TextObjectContents"))
public interface TextObjectContentsV2109 extends WrapperObject
{
    WrapperFactory<TextObjectContentsV2109> FACTORY = WrapperFactory.of(TextObjectContentsV2109.class);
}
