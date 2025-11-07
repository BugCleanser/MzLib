package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1400)
@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.text.NbtText", end = 1900),
        @VersionName(name = "net.minecraft.text.Text", begin = 1900)
    })
public interface TextNbtV1400 extends WrapperObject, Text
{
    WrapperFactory<TextNbtV1400> FACTORY = WrapperFactory.of(TextNbtV1400.class);

    // TODO
}
