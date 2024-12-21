package mz.mzlib.minecraft.text;

import com.google.gson.JsonObject;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.ListWrapper;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.network.chat.IChatBaseComponent",begin = 1403,end = 2005),
                @VersionName(name = "net.minecraft.network.chat.Component", begin=2005),
                @VersionName(name = "net.minecraft.text.Text", begin=1202)
        })
public interface Text extends WrapperObject
{
    @WrapperCreator
    static Text create(Object wrapped)
    {
        return WrapperObject.create(Text.class, wrapped);
    }

    static Text parse(JsonObject json)
    {
        return create(null).staticParse(json);
    }
    Text staticParse(JsonObject json);
    @SpecificImpl("staticParse")
    @VersionRange(begin = 1605,end = 2005)
    default Text staticParseV1605(JsonObject json){
        return ChatSerializerV1605_2005.parseComponent(json.getAsString());
    }
    @SpecificImpl("staticParse")
    @VersionRange(begin = 1202,end = 1605)
    default Text staticParseV1202(JsonObject json){
        //TODO
        return null;
    }
    @SpecificImpl("staticParse")
    @VersionRange(begin = 2005)
    default Text staticParseV2005(JsonObject json){
        //TODO
        return null;
    }

    @WrapMinecraftMethod(@VersionName(name="getLiteralString"))
    String getLiteralString();

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
        return TextMutableV1900.newInstance(TextContentLiteralV1900.Impl.newInstance(str), new ArrayList<>(), TextStyle.newInstance());
    }

    Text staticTranslatable(String key, Text ...args);
    static Text translatable(String key, Text ...args)
    {
        return create(null).staticTranslatable(key, args);
    }
    @SpecificImpl("staticTranslatable")
    @VersionRange(end=1900)
    default Text staticTranslatableV_1900(String key, Text ...args)
    {
        return TextTranslatableV_1900.newInstance(key, args);
    }
    @SpecificImpl("staticTranslatable")
    @VersionRange(begin=1900)
    default Text staticTranslatableV1900(String key, Text ...args)
    {
        return TextMutableV1900.newInstance(TextContentTranslatableV1900.newInstance(key, args), new ArrayList<>(), TextStyle.newInstance());
    }

    Text staticKeybind(String keybind);
    static Text keybind(String keybind)
    {
        return create(null).staticKeybind(keybind);
    }
    @SpecificImpl("staticKeybind")
    @VersionRange(end=1900)
    default Text staticKeybindV_1900(String keybind)
    {
        return TextKeybindV_1900.newInstance(keybind);
    }
    @SpecificImpl("staticKeybind")
    @VersionRange(begin=1900)
    default Text staticKeybindV1900(String keybind)
    {
        return TextMutableV1900.newInstance(TextContentKeybindV1900.newInstance(keybind), new ArrayList<>(), TextStyle.newInstance());
    }

    Text staticScore(TextScore value);
    static Text score(TextScore value)
    {
        return create(null).staticScore(value);
    }
    @SpecificImpl("staticScore")
    @VersionRange(end=1900)
    default Text staticScoreV_1900(TextScore value)
    {
        return value.castTo(TextScoreV_1900::create);
    }
    @SpecificImpl("staticScore")
    @VersionRange(begin=1900)
    default Text staticScoreV1900(TextScore value)
    {
        return TextMutableV1900.newInstance(value.castTo(TextContentScoreV1900::create), new ArrayList<>(), TextStyle.newInstance());
    }

    Text staticSelector(TextSelector selector);
    static Text selector(TextSelector selector)
    {
        return create(null).staticSelector(selector);
    }
    @SpecificImpl("staticSelector")
    @VersionRange(end=1900)
    default Text staticSelectorV_1900(TextSelector selector)
    {
        return selector.castTo(TextScoreV_1900::create);
    }
    @SpecificImpl("staticSelector")
    @VersionRange(begin=1900)
    default Text staticSelectorV1900(TextSelector selector)
    {
        return TextMutableV1900.newInstance(selector.castTo(TextContentScoreV1900::create), new ArrayList<>(), TextStyle.newInstance());
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
        return this.getContentV1900().castTo(TextContentLiteralV1900::create).getLiteral();
    }

    String getTranslatableKey();
    @SpecificImpl("getTranslatableKey")
    @VersionRange(end = 1900)
    default String getTranslatableKeyV_1900()
    {
        if (!this.isInstanceOf(TextTranslatableV_1900::create))
            return null;
        return this.castTo(TextTranslatableV_1900::create).getKey();
    }
    @SpecificImpl("getTranslatableKey")
    @VersionRange(begin = 1900)
    default String getTranslatableKeyV1900()
    {
        if(!this.getContentV1900().isInstanceOf(TextContentTranslatableV1900::create))
            return null;
        return this.getContentV1900().castTo(TextContentTranslatableV1900::create).getKey();
    }

    Text[] getTranslatableArgs();
    @SpecificImpl("getTranslatableArgs")
    @VersionRange(end=1900)
    default Text[] getTranslatableArgsV_1900()
    {
        if(!this.isInstanceOf(TextTranslatableV_1900::create))
            return null;
        return this.castTo(TextTranslatableV_1900::create).getArgs();
    }
    @SpecificImpl("getTranslatableArgs")
    @VersionRange(begin=1900)
    default Text[] getTranslatableArgsV1900()
    {
        if(!this.getContentV1900().isInstanceOf(TextContentTranslatableV1900::create))
            return null;
        return this.getContentV1900().castTo(TextContentTranslatableV1900::create).getArgs();
    }

    String getKeybind();
    @SpecificImpl("getKeybind")
    @VersionRange(end = 1900)
    default String getKeybindV_1900()
    {
        if (!this.isInstanceOf(TextKeybindV_1900::create))
            return null;
        return this.castTo(TextKeybindV_1900::create).getKeybind();
    }
    @SpecificImpl("getKeybind")
    @VersionRange(begin = 1900)
    default String getKeybindV1900()
    {
        if (!this.getContentV1900().isInstanceOf(TextContentKeybindV1900::create))
            return null;
        return this.getContentV1900().castTo(TextContentKeybindV1900::create).getKeybind();
    }

    TextScore getScore();
    @SpecificImpl("getScore")
    @VersionRange(end=1900)
    default TextScore getScoreV_1900()
    {
        if(!this.isInstanceOf(TextScore::create))
            return null;
        return this.castTo(TextScore::create);
    }
    @SpecificImpl("getScore")
    @VersionRange(begin=1900)
    default TextScore getScoreV1900()
    {
        if(!this.getContentV1900().isInstanceOf(TextScore::create))
            return null;
        return this.getContentV1900().castTo(TextScore::create);
    }

    TextSelector getSelector();
    @SpecificImpl("getSelector")
    @VersionRange(end = 1900)
    default TextSelectorV_1900 getSelectorV_1900()
    {
        if (!this.isInstanceOf(TextSelectorV_1900::create))
            return null;
        return this.castTo(TextSelectorV_1900::create);
    }
    @SpecificImpl("getSelector")
    @VersionRange(begin = 1900)
    default TextContentSelectorV1900 getSelectorV1900()
    {
        if (!this.getContentV1900().isInstanceOf(TextContentSelectorV1900::create))
            return null;
        return this.getContentV1900().castTo(TextContentSelectorV1900::create);
    }

    @WrapMinecraftMethod(@VersionName(name="getStyle"))
    TextStyle getStyle();

    void setStyle(TextStyle style);
    @SpecificImpl("setStyle")
    @WrapMinecraftMethod(@VersionName(name="setStyle", end=1900))
    void setStyleV_1900(TextStyle style);
    @SpecificImpl("setStyle")
    @VersionRange(begin = 1900)
    default void setStyleV1900(TextStyle style)
    {
        this.castTo(TextMutableV1900::create).setStyle(style);
    }
    // TODO
    default Text style(Consumer<TextStyle> consumer)
    {
        TextStyle style = this.getStyle();
        if (!style.isPresent())
        {
            style = TextStyle.newInstance();
            this.setStyle(style);
        }
        consumer.accept(style);
        return this;
    }
    @WrapMinecraftMethod(@VersionName(name="getSiblings"))
    List<?> getExtra0();
    default List<Text> getExtra()
    {
        List<?> result = this.getExtra0();
        if(result==null)
            return null;
        return new ListWrapper<>(result, Text::create);
    }
    default void setExtra(List<Text> value)
    {
        this.castTo(AbstractTextV_1900::create).setExtra(value);
    }
    default Text extra(Text ...value)
    {
        List<Text> extra = getExtra();
        if(extra==null)
        {
            extra=new ArrayList<>();
            this.setExtra(extra);
        }
        extra.addAll(Arrays.asList(value));
        return this;
    }

    @WrapMinecraftMethod(@VersionName(name="getContent", begin=1900))
    TextContentV1900 getContentV1900();
    
    default String toPlain()
    {
        // TODO
        return this.toString();
    }
    
    /**
     * @return json string
     */
    default String encode()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @WrapMinecraftInnerClass(outer = Text.class,name = @VersionName(name = "ChatSerializer",begin = 1605))
    interface ChatSerializerV1605_2005 extends WrapperObject{

        @WrapperCreator
        static ChatSerializerV1605_2005 create(Object wrapped)
        {
            return WrapperObject.create(ChatSerializerV1605_2005.class, wrapped);
        }

        static Text parseComponent(String json){
            return ChatSerializerV1605_2005.create(null).staticParseComponent(json);
        }

        //TODO
        @WrapMinecraftMethod(value = @VersionName(name = "b"))
        Text staticParseComponent(String json);
    }
}
