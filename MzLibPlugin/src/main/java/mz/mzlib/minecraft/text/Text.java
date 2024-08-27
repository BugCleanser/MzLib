package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
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

    static Text literal(String str)
    {
        return TextLiteral.newInstance(str);
    }
    static Text translatable(String key, Text ...args)
    {
        return TextTranslatable.newInstance(key, args);
    }
    static Text score(TextScore value)
    {
        return value;
    }
    static Text selector(String str)
    {
        return TextSelector.newInstance(str);
    }
    static Text keybind(String str)
    {
        return TextKeybind.newInstance(str);
    }
    default String getLiteral()
    {
        if(this.isInstanceOf(TextLiteral::create))
            return this.castTo(TextLiteral::create).getLiteral();
        else
            return null;
    }
    default String getTranslatableKey()
    {
        if(this.isInstanceOf(TextTranslatable::create))
            return this.castTo(TextTranslatable::create).getKey();
        else
            return null;
    }
    default Text[] getTranslatableArgs()
    {
        if(this.isInstanceOf(TextTranslatable::create))
            return this.castTo(TextTranslatable::create).getArgs();
        else
            return null;
    }
    default TextScore getScore()
    {
        if(this.isInstanceOf(TextScore::create))
            return this.castTo(TextScore::create);
        else
            return null;
    }
    default String getSelector()
    {
        if(this.isInstanceOf(TextSelector::create))
            return this.castTo(TextSelector::create).getSelector();
        else
            return null;
    }
    default String getKeybind()
    {
        if (this.isInstanceOf(TextKeybind::create))
            return this.castTo(TextKeybind::create).getKeybind();
        else
            return null;
    }
    default TextStyle getStyle()
    {
        return this.castTo(AbstractText::create).getStyle();
    }
    default void setStyle(TextStyle value)
    {
        this.castTo(AbstractText::create).setStyle(value);
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
        return this.castTo(AbstractText::create).getExtra();
    }
    default void setExtra(List<Text> value)
    {
        this.castTo(AbstractText::create).setExtra(value);
    }
    default Text extra(Text ...value)
    {
        this.setExtra(new ArrayList<>(Arrays.asList(value)));
        return this;
    }
}
