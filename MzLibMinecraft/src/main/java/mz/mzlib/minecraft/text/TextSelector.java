package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

import java.util.Optional;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.SelectorText", end = 1400),
                @VersionName(name = "net.minecraft.network.chat.SelectorComponent", begin = 1400, end = 1403),
                @VersionName(name = "net.minecraft.text.SelectorText", begin = 1403, end = 1900),
                @VersionName(name = "net.minecraft.text.SelectorTextContent", begin=1900)
        })
public interface TextSelector extends WrapperObject
{
    WrapperFactory<TextSelector> FACTORY = WrapperFactory.find(TextSelector.class);
    @Deprecated
    @WrapperCreator
    static TextSelector create(Object wrapped)
    {
        return WrapperObject.create(TextSelector.class, wrapped);
    }

    TextSelector staticNewInstance(String pattern);
    static TextSelector newInstance(String pattern)
    {
        return create(null).staticNewInstance(pattern);
    }
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1700)
    @WrapConstructor
    TextSelector staticNewInstanceV_1700(String pattern);
    @VersionRange(begin=1700, end=2102)
    @WrapConstructor
    TextSelector staticNewInstance0V1700_2102(String pattern, Optional<?> separator0);
    static TextSelector newInstance0V1700_2102(String pattern, Optional<?> separator0)
    {
        return create(null).staticNewInstance0V1700_2102(pattern, separator0);
    }
    static TextSelector newInstanceV1700_2102(String pattern, Text separator)
    {
        return newInstance0V1700_2102(pattern, Optional.ofNullable(separator).map(Text::getWrapped));
    }
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1700, end=2102)
    default TextSelector staticNewInstanceV1700_2102(String pattern)
    {
        return this.staticNewInstance0V1700_2102(pattern, Optional.empty());
    }
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=2102)
    default TextSelector staticNewInstanceV2102(String pattern)
    {
        throw new UnsupportedOperationException(); // TODO
    }

    @VersionRange(end=2102)
    @WrapMinecraftMethod(@VersionName(name="getPattern"))
    String getPatternV_2102();
    @WrapMinecraftFieldAccessor(@VersionName(name="separator", begin=1700))
    Optional<?> getSeparator0V1700();
    default Text getSeparatorV1700()
    {
        return Text.create(this.getSeparator0V1700().orElse(null));
    }
}
