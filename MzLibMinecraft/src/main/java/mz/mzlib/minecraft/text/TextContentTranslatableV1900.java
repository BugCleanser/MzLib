package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

import java.util.Arrays;

@VersionRange(begin=1900)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.text.TranslatableTextContent"))
public interface TextContentTranslatableV1900 extends WrapperObject, TextContentV1900
{
    WrapperFactory<TextContentTranslatableV1900> FACTORY = WrapperFactory.of(TextContentTranslatableV1900.class);
    
    static TextContentTranslatableV1900 newInstance0(String key, Object[] args)
    {
        return FACTORY.getStatic().static$newInstance0(key, args);
    }
    TextContentTranslatableV1900 static$newInstance0(String key, Object[] args);
    @SpecificImpl("static$newInstance0")
    @VersionRange(end=1904)
    @WrapConstructor
    TextContentTranslatableV1900 static$newInstance0V_1904(String key, Object[] args);
    @VersionRange(begin=1904)
    @WrapConstructor
    TextContentTranslatableV1900 static$newInstance0V1904(String key, String fallback, Object[] args);
    @SpecificImpl("static$newInstance0")
    @VersionRange(begin=1904)
    default TextContentTranslatableV1900 static$newInstance0V1904(String key, Object[] args)
    {
        return this.static$newInstance0V1904(key, null, args);
    }
    
    @VersionRange(begin=1904)
    static TextContentTranslatableV1900 newInstance0V1904(String key, String fallback, Object[] args)
    {
        return FACTORY.getStatic().static$newInstance0V1904(key, fallback, args);
    }

    @WrapMinecraftMethod(@VersionName(name="getKey"))
    String getKey();

    @WrapMinecraftMethod(@VersionName(name="getArgs"))
    Object[] getArgs0();
    
    @VersionRange(begin=1904)
    @WrapMinecraftMethod(@VersionName(name="getFallback"))
    String getFallbackV1904();
}
