package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.text.TextContent", begin = 1900))
public interface TextContentV1900 extends WrapperObject
{
    @WrapperCreator
    static TextContentV1900 create(Object wrapped)
    {
        return WrapperObject.create(TextContentV1900.class, wrapped);
    }
}
