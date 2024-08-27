package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Arrays;
import java.util.Objects;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.TranslatableText"))
public interface TextTranslatable extends WrapperObject, AbstractText
{
    @WrapperCreator
    static TextTranslatable create(Object wrapped)
    {
        return WrapperObject.create(TextTranslatable.class, wrapped);
    }

    @WrapConstructor
    TextTranslatable staticNewInstance0(String key, Object[] args);
    static TextTranslatable newInstance0(String key, Object[] args)
    {
        return create(null).staticNewInstance0(key, args);
    }
    static TextTranslatable newInstance(String key, Text[] args)
    {
        return newInstance0(key, Arrays.stream(args).map(Text::getWrapped).toArray());
    }

    @WrapMinecraftMethod(@VersionName(name="getKey"))
    String getKey();

    @WrapMinecraftMethod(@VersionName(name="getArgs"))
    Object[] getArgs0();
    default Text[] getArgs()
    {
        return Arrays.stream(this.getArgs0()).map(a -> WrapperObject.create(a).isInstanceOf(Text::create) ? Text.create(a) : Text.literal(Objects.toString(a)) ).toArray(Text[]::new);
    }
}
