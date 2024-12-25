package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
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
    
    static TextMutableV1600 newInstanceV1900(TextContentV1900 content, List<?> extra, TextStyle style)
    {
        return create(null).staticNewInstanceV1900(content, extra, style);
    }
    @VersionRange(begin=1900)
    @WrapConstructor
    TextMutableV1600 staticNewInstanceV1900(TextContentV1900 content, List<?> extra, TextStyle style);
}
