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
    @Deprecated
    @WrapperCreator
    static TextContentTranslatableV1900 create(Object wrapped)
    {
        return WrapperObject.create(TextContentTranslatableV1900.class, wrapped);
    }

    TextContentTranslatableV1900 staticNewInstance0(String key, Object[] args);
    @SpecificImpl("staticNewInstance0")
    @VersionRange(end=1904)
    @WrapConstructor
    TextContentTranslatableV1900 staticNewInstance0V_1904(String key, Object[] args);
    @VersionRange(begin=1904)
    @WrapConstructor
    TextContentTranslatableV1900 staticNewInstance0V1904(String key, String fallback, Object[] args);
    @SpecificImpl("staticNewInstance0")
    @VersionRange(begin=1904)
    default TextContentTranslatableV1900 staticNewInstance0V1904(String key, Object[] args)
    {
        return this.staticNewInstance0V1904(key, null, args);
    }
    static TextContentTranslatableV1900 newInstance0(String key, Object[] args)
    {
        return create(null).staticNewInstance0(key, args);
    }
    static TextContentTranslatableV1900 newInstance(String key, Text[] args)
    {
        return newInstance0(key, Arrays.stream(args).map(Text::getWrapped).toArray());
    }

    @WrapMinecraftMethod(@VersionName(name="getKey"))
    String getKey();

    @WrapMinecraftMethod(@VersionName(name = "getArgs"))
    Object[] getArgs0();
}
