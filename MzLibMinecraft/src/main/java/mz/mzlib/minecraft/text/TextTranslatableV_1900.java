package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Arrays;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.TranslatableText", end=1400),
                @VersionName(name = "net.minecraft.network.chat.TranslatableComponent", begin=1400, end=1403),
                @VersionName(name = "net.minecraft.text.TranslatableText", begin=1403, end=1900)
        })
public interface TextTranslatableV_1900 extends WrapperObject, AbstractTextV_1900, TextTranslatableCommon
{
    WrapperFactory<TextTranslatableV_1900> FACTORY = WrapperFactory.of(TextTranslatableV_1900.class);
    @Deprecated
    @WrapperCreator
    static TextTranslatableV_1900 create(Object wrapped)
    {
        return WrapperObject.create(TextTranslatableV_1900.class, wrapped);
    }

    @WrapConstructor
    TextTranslatableV_1900 staticNewInstance0(String key, Object[] args);
    static TextTranslatableV_1900 newInstance0(String key, Object[] args)
    {
        return create(null).staticNewInstance0(key, args);
    }
    static TextTranslatableV_1900 newInstance(String key, Text[] args)
    {
        return newInstance0(key, Arrays.stream(args).map(Text::getWrapped).toArray());
    }

    @WrapMinecraftMethod(@VersionName(name="getKey"))
    String getKey();

    @Override
    @WrapMinecraftMethod(
            {
                    @VersionName(name = "getArgs", end = 1400),
                    @VersionName(name = "getParams", begin = 1400, end = 1403),
                    @VersionName(name = "getArgs", begin = 1403)
            })
    Object[] getArgs0();
}
