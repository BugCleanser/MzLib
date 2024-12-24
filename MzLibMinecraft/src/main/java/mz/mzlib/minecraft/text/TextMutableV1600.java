package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.text.MutableText", begin=1600))
public interface TextMutableV1600 extends Text
{
    @WrapperCreator
    static TextMutableV1600 create(Object wrapped)
    {
        return WrapperObject.create(TextMutableV1600.class, wrapped);
    }

    @WrapConstructor
    TextMutableV1600 staticNewInstance(TextContentV1900 content, List<?> extra, TextStyle style);
    static TextMutableV1600 newInstance(TextContentV1900 content, List<?> extra, TextStyle style)
    {
        return create(null).staticNewInstance(content, extra, style);
    }
}
