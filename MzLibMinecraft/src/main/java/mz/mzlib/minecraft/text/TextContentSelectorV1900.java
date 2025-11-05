package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.Result;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;

@VersionRange(begin=1900)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.SelectorTextContent"))
public interface TextContentSelectorV1900 extends WrapperObject, TextContentV1900
{
    WrapperFactory<TextContentSelectorV1900> FACTORY = WrapperFactory.of(TextContentSelectorV1900.class);
    
    static TextContentSelectorV1900 newInstance(String selector, Option<Text> separator)
    {
        return FACTORY.getStatic().static$newInstance(selector, separator);
    }
    TextContentSelectorV1900 static$newInstance(String selector, Option<Text> separator);
    @SpecificImpl("static$newInstance")
    @VersionRange(end=2102)
    default TextContentSelectorV1900 static$newInstanceV_2102(String selector, Option<Text> separator)
    {
        return FACTORY.getStatic().static$newInstance0V_2102(selector, separator.map(Text::getWrapped).toOptional());
    }
    @VersionRange(end=2102)
    @WrapConstructor
    TextContentSelectorV1900 static$newInstance0V_2102(String selector, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<?> separator0);
    @SpecificImpl("static$newInstance")
    @VersionRange(begin=2102)
    default TextContentSelectorV1900 static$newInstanceV2102(String selector, Option<Text> separator)
    {
        Result<Option<ParsedSelectorV2102>, String> parse = ParsedSelectorV2102.parse(selector);
        for(String err: parse.getError())
        {
            throw new IllegalArgumentException(err);
        }
        return newInstanceV2102(parse.getValue().unwrap(), separator);
    }
    
    static TextContentSelectorV1900 newInstanceV2102(ParsedSelectorV2102 selector, Option<Text> separator)
    {
        return FACTORY.getStatic().static$newInstance0V2102(selector, separator.map(Text::getWrapped).toOptional());
    }
    @VersionRange(begin=2102)
    @WrapConstructor
    TextContentSelectorV1900 static$newInstance0V2102(ParsedSelectorV2102 selector, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<?> separator);
    
    String getSelector();
    @SpecificImpl("getSelector")
    @VersionRange(end=2102)
    @WrapMinecraftMethod(@VersionName(name="getPattern"))
    String getSelectorV_2102();
    @SpecificImpl("getSelector")
    @VersionRange(begin=2102)
    default String getSelector1V2102()
    {
        return this.getSelectorV2102().getUnparsed();
    }
    
    @VersionRange(begin=2102)
    @WrapMinecraftMethod(@VersionName(name="selector"))
    ParsedSelectorV2102 getSelectorV2102();
    
    default Option<Text> getSeparator()
    {
        return Option.fromOptional(this.getSeparator0()).map(Text.FACTORY::create);
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="separator"))
    Optional<?> getSeparator0();
}
