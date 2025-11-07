package mz.mzlib.minecraft.text.object;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2109)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.text.object.AtlasTextObjectContents"))
public interface TextObjectContentsAtlasV2109 extends WrapperObject, TextObjectContentsV2109
{
    WrapperFactory<TextObjectContentsAtlasV2109> FACTORY = WrapperFactory.of(TextObjectContentsAtlasV2109.class);

    static TextObjectContentsAtlasV2109 newInstance(Identifier atlas, Identifier sprite)
    {
        return FACTORY.getStatic().static$newInstance(atlas, sprite);
    }
    @WrapConstructor
    TextObjectContentsAtlasV2109 static$newInstance(Identifier atlas, Identifier sprite);
}
