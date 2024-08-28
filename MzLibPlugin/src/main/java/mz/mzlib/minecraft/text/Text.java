package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.Text", end=1400),
                @VersionName(name = "net.minecraft.network.chat.Component", begin=1400, end=1403),
                @VersionName(name = "net.minecraft.text.Text", begin=1403)
        })
public interface Text extends WrapperObject
{
    @WrapperCreator
    static Text create(Object wrapped)
    {
        return WrapperObject.create(Text.class, wrapped);
    }

    Text staticLiteral(String str);
    static Text literal(String str)
    {
        return create(null).staticLiteral(str);
    }
    @SpecificImpl("staticLiteral")
    @VersionRange(end=1900)
    default Text staticLiteralV_1900(String str)
    {
        return TextLiteralV_1900.newInstance(str);
    }
    @SpecificImpl("staticLiteral")
    @VersionRange(begin=1900)
    default Text staticLiteralV1900(String str)
    {
        return TextMutableV1900.newInstance(TextContentLiteralV1900.Impl.newInstance(str), new ArrayList<>(), TextStyle.create(null));
    }

    static Text translatable(String key, Text ...args)
    {
        return TextTranslatableV_1900.newInstance(key, args);
    }
    static Text score(TextScore value)
    {
        return value.castTo(TextScoreV_1900::create);
    }
    static Text selector(String str)
    {
        return TextSelectorV_1900.newInstance(str);
    }
    static Text keybind(String str)
    {
        return TextKeybindV_1900.newInstance(str);
    }

    String getLiteral();
    @SpecificImpl("getLiteral")
    @VersionRange(end=1900)
    default String getLiteralV_1900()
    {
        if(!this.isInstanceOf(TextLiteralV_1900::create))
            return null;
        return this.castTo(TextLiteralV_1900::create).getLiteral();
    }
    @SpecificImpl("getLiteral")
    @VersionRange(begin=1900)
    default String getLiteralV1900()
    {
        if(!this.getContentV1900().isInstanceOf(TextContentLiteralV1900::create))
            return null;
        return this.castTo(TextContentLiteralV1900::create).getLiteral();
    }
    default String getTranslatableKey()
    {
        if(!this.isInstanceOf(TextTranslatableV_1900::create))
            return null;
        return this.castTo(TextTranslatableV_1900::create).getKey();
    }
    default Text[] getTranslatableArgs()
    {
        if(!this.isInstanceOf(TextTranslatableV_1900::create))
            return null;
        return this.castTo(TextTranslatableV_1900::create).getArgs();
    }
    default TextScore getScore()
    {
        if(!this.isInstanceOf(TextScore::create))
            return null;
        return this.castTo(TextScore::create);
    }
    default String getSelector()
    {
        if(!this.isInstanceOf(TextSelectorV_1900::create))
            return null;
        return this.castTo(TextSelectorV_1900::create).getSelector();
    }
    default String getKeybind()
    {
        if (!this.isInstanceOf(TextKeybindV_1900::create))
            return null;
        return this.castTo(TextKeybindV_1900::create).getKeybind();
    }
    default TextStyle getStyle()
    {
        return this.castTo(AbstractTextV_1900::create).getStyle();
    }
    default void setStyle(TextStyle value)
    {
        this.castTo(AbstractTextV_1900::create).setStyle(value);
    }
    default Text style(Consumer<TextStyle> consumer)
    {
        TextStyle style = this.getStyle();
        if(!style.isPresent())
        {
            style = TextStyle.newInstance();
            this.setStyle(style);
        }
        consumer.accept(style);
        return this;
    }
    default List<Text> getExtra()
    {
        return this.castTo(AbstractTextV_1900::create).getExtra();
    }
    default void setExtra(List<Text> value)
    {
        this.castTo(AbstractTextV_1900::create).setExtra(value);
    }
    default Text extra(Text ...value)
    {
        this.setExtra(new ArrayList<>(Arrays.asList(value)));
        return this;
    }

    @WrapMinecraftMethod(@VersionName(name="getContent", begin=1900))
    TextContentV1900 getContentV1900();
}
