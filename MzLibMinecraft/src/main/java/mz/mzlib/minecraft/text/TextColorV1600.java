package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.TextColor", begin = 1600))
public interface TextColorV1600 extends WrapperObject
{
    @WrapperCreator
    static TextColorV1600 create(Object wrapped)
    {
        return WrapperObject.create(TextColorV1600.class, wrapped);
    }
    
    static TextColorV1600 fromRgb(int rgb)
    {
        return create(null).staticFromRgb(rgb);
    }
    @WrapMinecraftMethod(@VersionName(name="fromRgb"))
    TextColorV1600 staticFromRgb(int rgb);
    
    @WrapMinecraftMethod(@VersionName(name="getRgb"))
    int getRgb();
    
    static TextColorV1600 fromLegacy(TextFormatLegacy format)
    {
        return create(null).staticFromLegacy(format);
    }
    @WrapMinecraftMethod(@VersionName(name="fromFormatting"))
    TextColorV1600 staticFromLegacy(TextFormatLegacy format);
}
