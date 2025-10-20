package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Arrays;
import java.util.List;

@WrapMinecraftClass(
        {
                @VersionName(name="net.minecraft.text.TranslatableText", end=1900),
                @VersionName(name="net.minecraft.text.Text", begin=1900)
        })
public interface TextTranslatable extends WrapperObject, Text
{
    WrapperFactory<TextTranslatable> FACTORY = WrapperFactory.of(TextTranslatable.class);
    
    static TextTranslatable newInstance(String key, Object ...args)
    {
        return newInstance(key, Arrays.asList(args));
    }
    static TextTranslatable newInstance(String key, List<Object> args)
    {
        return FACTORY.getStatic().staticNewInstance0(key, args.stream().map(FUNCTION_ARGS0.invert()).toArray());
    }
    TextTranslatable staticNewInstance0(String key, Object[] args);
    @SpecificImpl("staticNewInstance0")
    @VersionRange(end=1900)
    @WrapConstructor
    TextTranslatable staticNewInstance0V_1900(String key, Object[] args);
    @SpecificImpl("staticNewInstance0")
    @VersionRange(begin=1900)
    default TextTranslatable staticNewInstance0V1900(String key, Object[] args)
    {
        return TextMutableV1600.newInstanceV1900(TextContentTranslatableV1900.newInstance0(key, args)).as(FACTORY);
    }
    
    static TextTranslatable newInstanceV1904(String key, String fallback, Object ...args)
    {
        return newInstanceV1904(key, fallback, Arrays.asList(args));
    }
    static TextTranslatable newInstanceV1904(String key, String fallback, List<Object> args)
    {
        return newInstance0V1904(key, fallback, args.stream().map(FUNCTION_ARGS0.invert()).toArray());
    }
    static TextTranslatable newInstance0V1904(String key, String fallback, Object[] args)
    {
        return TextMutableV1600.newInstanceV1900(TextContentTranslatableV1900.newInstance0V1904(key, fallback, args)).as(FACTORY);
    }
    
    String getKey();
    @SpecificImpl("getKey")
    @VersionRange(end=1900)
    @WrapMinecraftMethod(@VersionName(name="getKey"))
    String getKeyV_1900();
    @SpecificImpl("getKey")
    @VersionRange(begin=1900)
    default String getKeyV1900()
    {
        return this.getContentV1900().castTo(TextContentTranslatableV1900.FACTORY).getKey();
    }
    
    InvertibleFunction<Object, Object> FUNCTION_ARGS0 = new InvertibleFunction<>(a0->
    {
        if(WrapperObject.FACTORY.create(a0).isInstanceOf(Text.FACTORY))
            return Text.FACTORY.create(a0);
        return a0;
    }, a->
    {
        if(a instanceof Text)
            return ((Text)a).getWrapped();
        return a;
    });
    default List<Object> getArgs()
    {
        return new ListProxy<>(Arrays.asList(this.getArgs0()), FUNCTION_ARGS0);
    }
    Object[] getArgs0();
    @SpecificImpl("getArgs0")
    @VersionRange(end=1900)
    @WrapMinecraftMethod(@VersionName(name = "getArgs"))
    Object[] getArgs0V_1900();
    @SpecificImpl("getArgs0")
    @VersionRange(begin=1900)
    default Object[] getArgs0V1900()
    {
        return this.getContentV1900().castTo(TextContentTranslatableV1900.FACTORY).getArgs0();
    }
    
    @VersionRange(begin=1904)
    default String getFallbackV1904()
    {
        return this.getContentV1900().as(TextContentTranslatableV1900.FACTORY).getFallbackV1904();
    }
}
