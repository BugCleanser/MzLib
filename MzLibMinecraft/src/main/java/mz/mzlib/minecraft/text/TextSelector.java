package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.*;

import java.util.Optional;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.SelectorText", end = 1900),
                @VersionName(name = "net.minecraft.text.Text", begin=1900)
        })
public interface TextSelector extends WrapperObject, Text
{
    WrapperFactory<TextSelector> FACTORY = WrapperFactory.of(TextSelector.class);
    
    static TextSelector newInstance(String selector)
    {
        return FACTORY.getStatic().staticNewInstance(selector);
    }
    TextSelector staticNewInstance(String selector);
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1700)
    @WrapConstructor
    TextSelector staticNewInstanceV_1700(String selector);
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1700)
    default TextSelector staticNewInstanceV1700(String selector)
    {
        return newInstanceV1700(selector, Option.none());
    }
    
    @VersionRange(begin=1700)
    static TextSelector newInstanceV1700(String selector, Option<Text> separator)
    {
        return FACTORY.getStatic().staticNewInstanceV1700(selector, separator);
    }
    @VersionRange(begin=1700)
    TextSelector staticNewInstanceV1700(String selector, Option<Text> separator);
    @SpecificImpl("staticNewInstanceV1700")
    @VersionRange(begin=1700, end=1900)
    default TextSelector staticNewInstanceV1700_1900(String selector, Option<Text> separator)
    {
        return this.staticNewInstance0V1700_1900(selector, separator.map(Text::getWrapped).toOptional());
    }
    @VersionRange(begin=1700, end=1900)
    @WrapConstructor
    TextSelector staticNewInstance0V1700_1900(String selector, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<?> separator0);
    @SpecificImpl("staticNewInstanceV1700")
    @VersionRange(begin=1900)
    default TextSelector staticNewInstanceV1900(String selector, Option<Text> separator)
    {
        return TextMutableV1600.newInstanceV1900(TextContentSelectorV1900.newInstance(selector, separator)).as(FACTORY);
    }
    
    @VersionRange(begin=2102)
    default TextSelector staticNewInstanceV2102(ParsedSelectorV2102 selector, Option<Text> separator)
    {
        return TextMutableV1600.newInstanceV1900(TextContentSelectorV1900.newInstanceV2102(selector, separator)).as(FACTORY);
    }
    
    String getSelector();
    @SpecificImpl("getSelector")
    @VersionRange(end=1900)
    @WrapMinecraftMethod(@VersionName(name="getPattern"))
    String getSelectorV_1900();
    @SpecificImpl("getSelector")
    @VersionRange(begin=1900)
    default String getSelectorV1900()
    {
        return this.getContentV1900().as(TextContentSelectorV1900.FACTORY).getSelector();
    }
    
    default ParsedSelectorV2102 getSelectorV2102()
    {
        return this.getContentV1900().as(TextContentSelectorV1900.FACTORY).getSelectorV2102();
    }
    
    @VersionRange(begin=1700)
    Option<Text> getSeparatorV1700();
    @SpecificImpl("getSeparatorV1700")
    @VersionRange(begin=1700, end=1900)
    default Option<Text> getSeparatorV1700_1900()
    {
        return Option.fromOptional(this.getSeparator0V1700_1900()).map(Text.FACTORY::create);
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="separator", begin=1700))
    Optional<?> getSeparator0V1700_1900();
    @SpecificImpl("getSeparatorV1700")
    @VersionRange(begin=1900)
    default Option<Text> getSeparatorV1900()
    {
        return this.getContentV1900().as(TextContentSelectorV1900.FACTORY).getSeparator();
    }
}
