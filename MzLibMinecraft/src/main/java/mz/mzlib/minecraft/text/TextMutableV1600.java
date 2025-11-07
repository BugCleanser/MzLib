package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.*;

import java.util.ArrayList;
import java.util.List;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.text.MutableText", begin = 1600))
public interface TextMutableV1600 extends Text
{
    WrapperFactory<TextMutableV1600> FACTORY = WrapperFactory.of(TextMutableV1600.class);
    @Deprecated
    @WrapperCreator
    static TextMutableV1600 create(Object wrapped)
    {
        return WrapperObject.create(TextMutableV1600.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "siblings", begin = 1900))
    void setExtra0V1900(List<Object> value);

    @SpecificImpl("setExtra")
    @VersionRange(begin = 1900)
    @Override
    default Text setExtraV1900(List<Text> value)
    {
        this.setExtra0V1900(new ListProxy<>(value, InvertibleFunction.wrapper(Text.FACTORY).inverse()));
        return this;
    }

    static TextMutableV1600 newInstanceV1900(TextContentV1900 content)
    {
        return newInstanceV1900(content, new ArrayList<>(), TextStyle.empty());
    }
    static TextMutableV1600 newInstanceV1900(TextContentV1900 content, List<?> extra, TextStyle style)
    {
        return FACTORY.getStatic().static$newInstanceV1900(content, extra, style);
    }
    @VersionRange(begin = 1900)
    @WrapConstructor
    TextMutableV1600 static$newInstanceV1900(TextContentV1900 content, List<?> extra, TextStyle style);

    @WrapMinecraftMethod(@VersionName(name = "setStyle"))
    void setStyleV1600(TextStyle style);

    @SpecificImpl("setStyle")
    @VersionRange(begin = 1900)
    @Override
    default void setStyleV1900(TextStyle style)
    {
        this.setStyleV1600(style);
    }
}
