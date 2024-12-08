package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.text.MutableText", begin=1900))
public interface TextMutableV1900 extends Text
{
    @WrapperCreator
    static TextMutableV1900 create(Object wrapped)
    {
        return WrapperObject.create(TextMutableV1900.class, wrapped);
    }

    @WrapConstructor
    TextMutableV1900 staticNewInstance(TextContentV1900 content, List<?> extra, TextStyle style);
    static TextMutableV1900 newInstance(TextContentV1900 content, List<?> extra, TextStyle style)
    {
        return create(null).staticNewInstance(content, extra, style);
    }
}
