package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1600)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.TextColor"))
public interface TextColorV1600 extends WrapperObject
{
    WrapperFactory<TextColorV1600> FACTORY = WrapperFactory.find(TextColorV1600.class);
    @Deprecated
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
    
    @WrapMinecraftFieldAccessor(@VersionName(name="rgb"))
    int getRgb();
    
    static TextColorV1600 fromLegacy(TextFormatLegacy format)
    {
        return create(null).staticFromLegacy(format);
    }
    @WrapMinecraftMethod(@VersionName(name="fromFormatting"))
    TextColorV1600 staticFromLegacy(TextFormatLegacy format);
}
