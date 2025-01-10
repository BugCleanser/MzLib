package mz.mzlib.minecraft.text;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.entry.RegistryEntryLookupV1903;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.ListWrapper;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WrapMinecraftClass({@VersionName(name="net.minecraft.text.Text")})
public interface Text extends WrapperObject
{
    @WrapperCreator
    static Text create(Object wrapped)
    {
        return WrapperObject.create(Text.class, wrapped);
    }
    
    static Text decode(JsonElement json)
    {
        return Serializer.decode(json);
    }
    
    static Text decode(String json)
    {
        return decode(new Gson().fromJson(json, JsonElement.class));
    }
    
    default JsonElement encode()
    {
        return Serializer.encode(this);
    }
    
    Text staticLiteral(String str);
    
    static Text literal(String str)
    {
        return create(null).staticLiteral(str);
    }
    
    static Text fromLiteral(String literal)
    {
        return literal(literal); // FIXME
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
        return TextMutableV1600.newInstanceV1900(TextContentLiteralV1900.newInstance(str), new ArrayList<>(), TextStyle.empty());
    }
    
    Text staticTranslatable(String key, Text... args);
    
    static Text translatable(String key, Text... args)
    {
        return create(null).staticTranslatable(key, args);
    }
    
    @SpecificImpl("staticTranslatable")
    @VersionRange(end=1900)
    default Text staticTranslatableV_1900(String key, Text... args)
    {
        return TextTranslatableV_1900.newInstance(key, args);
    }
    
    @SpecificImpl("staticTranslatable")
    @VersionRange(begin=1900)
    default Text staticTranslatableV1900(String key, Text... args)
    {
        return TextMutableV1600.newInstanceV1900(TextContentTranslatableV1900.newInstance(key, args), new ArrayList<>(), TextStyle.empty());
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
        return TextMutableV1600.newInstanceV1900(TextContentKeybindV1900.newInstance(keybind), new ArrayList<>(), TextStyle.empty());
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
        return TextMutableV1600.newInstanceV1900(value.castTo(TextContentScoreV1900::create), new ArrayList<>(), TextStyle.empty());
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
        return TextMutableV1600.newInstanceV1900(selector.castTo(TextContentScoreV1900::create), new ArrayList<>(), TextStyle.empty());
    }
    
    String getLiteral();
    
    @SpecificImpl("getLiteral")
    @VersionRange(end=1900)
    default String getLiteralV_1900()
    {
        if(!this.isInstanceOf(TextLiteralV_1900::create))
            return null;
        return this.castTo(TextLiteralV_1900::create).getLiteralV_1900();
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
    @VersionRange(end=1900)
    default String getTranslatableKeyV_1900()
    {
        if(!this.isInstanceOf(TextTranslatableV_1900::create))
            return null;
        return this.castTo(TextTranslatableV_1900::create).getKey();
    }
    
    @SpecificImpl("getTranslatableKey")
    @VersionRange(begin=1900)
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
    @VersionRange(end=1900)
    default String getKeybindV_1900()
    {
        if(!this.isInstanceOf(TextKeybindV_1900::create))
            return null;
        return this.castTo(TextKeybindV_1900::create).getKey();
    }
    
    @SpecificImpl("getKeybind")
    @VersionRange(begin=1900)
    default String getKeybindV1900()
    {
        if(!this.getContentV1900().isInstanceOf(TextContentKeybindV1900::create))
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
    @VersionRange(end=1900)
    default TextSelectorV_1900 getSelectorV_1900()
    {
        if(!this.isInstanceOf(TextSelectorV_1900::create))
            return null;
        return this.castTo(TextSelectorV_1900::create);
    }
    
    @SpecificImpl("getSelector")
    @VersionRange(begin=1900)
    default TextContentSelectorV1900 getSelectorV1900()
    {
        if(!this.getContentV1900().isInstanceOf(TextContentSelectorV1900::create))
            return null;
        return this.getContentV1900().castTo(TextContentSelectorV1900::create);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getStyle"))
    TextStyle getStyle();
    
    void setStyle(TextStyle style);
    
    @SpecificImpl("setStyle")
    @VersionRange(end=1900)
    default void setStyleV_1900(TextStyle style)
    {
        this.castTo(AbstractTextV_1900::create).setStyleV_1900(style);
    }
    
    @SpecificImpl("setStyle")
    @VersionRange(begin=1900)
    default void setStyleV1900(TextStyle style)
    {
        this.castTo(TextMutableV1600::create).setStyleV1900(style);
    }
    
    default TextStyle style()
    {
        TextStyle result = this.getStyle();
        if(!result.isPresent())
            this.setStyle(result = TextStyle.empty());
        return result;
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
    
    Text setExtra(List<Text> value);
    
    @SpecificImpl("setExtra")
    @VersionRange(end=1900)
    default Text setExtraV_1900(List<Text> value)
    {
        return this.castTo(AbstractTextV_1900::create).setExtraV_1900(value);
    }
    
    @SpecificImpl("setExtra")
    @VersionRange(begin=1900)
    default Text setExtraV1900(List<Text> value)
    {
        return this.castTo(TextMutableV1600::create).setExtraV1900(value);
    }
    
    default Text addExtra(Text... value)
    {
        List<Text> extra = getExtra();
        if(extra==null)
        {
            extra = new ArrayList<>();
            this.setExtra(extra);
        }
        extra.addAll(Arrays.asList(value));
        return this;
    }
    
    TextColor getColor();
    
    @SpecificImpl("getColor")
    @VersionRange(end=1600)
    default TextColor getColorV_1600()
    {
        TextFormatLegacy result = this.style().getColorV_1600();
        if(!result.isPresent())
            return null;
        return new TextColor(result);
    }
    
    @VersionRange(begin=1600)
    default TextColor getColorV1600()
    {
        TextColorV1600 result = this.style().getColorV1600();
        if(!result.isPresent())
            return null;
        return new TextColor(result);
    }
    
    Text setColor(TextColor value);
    
    @SpecificImpl("setColor")
    @VersionRange(end=1600)
    default Text setColorV_1600(TextColor value)
    {
        this.style().setColorV_1600(value!=null ? value.legacy : TextFormatLegacy.create(null));
        return this;
    }
    
    @SpecificImpl("setColor")
    @VersionRange(begin=1600)
    default Text setColorV1600(TextColor value)
    {
        this.setStyle(this.style().withColorV1600(value!=null ? value.v1600 : TextColorV1600.create(null)));
        return this;
    }
    
    default Boolean getBold()
    {
        return this.style().getBold();
    }
    
    Text setBold(Boolean bold);
    
    @SpecificImpl("setBold")
    @VersionRange(end=1600)
    default Text setBoldV_1600(Boolean bold)
    {
        this.style().setBold(bold);
        return this;
    }
    
    @SpecificImpl("setBold")
    @VersionRange(begin=1600)
    default Text setBoldV1600(Boolean bold)
    {
        this.setStyle(this.style().withBoldV1600(bold));
        return this;
    }
    
    default Boolean getItalic()
    {
        return this.style().getItalic();
    }
    
    Text setItalic(Boolean italic);
    
    @SpecificImpl("setItalic")
    @VersionRange(end=1600)
    default Text setItalicV_1600(Boolean italic)
    {
        this.style().setItalic(italic);
        return this;
    }
    
    @SpecificImpl("setItalic")
    @VersionRange(begin=1600)
    default Text setItalicV1600(Boolean italic)
    {
        this.setStyle(this.style().withItalicV1600(italic));
        return this;
    }
    
    default Boolean getUnderlined()
    {
        return this.style().getUnderlined();
    }
    
    Text setUnderlined(Boolean underlined);
    
    @SpecificImpl("setUnderlined")
    @VersionRange(end=1600)
    default Text setUnderlinedV_1600(Boolean underlined)
    {
        this.style().setUnderlined(underlined);
        return this;
    }
    
    @SpecificImpl("setUnderlined")
    @VersionRange(begin=1600)
    default Text setUnderlinedV1600(Boolean underlined)
    {
        this.setStyle(this.style().withUnderlinedV1600(underlined));
        return this;
    }
    
    default Boolean getStrikethrough()
    {
        return this.style().getStrikethrough();
    }
    
    Text setStrikethrough(Boolean strikethrough);
    
    @SpecificImpl("setStrikethrough")
    @VersionRange(end=1600)
    default Text setStrikethroughV_1600(Boolean strikethrough)
    {
        this.style().setStrikethrough(strikethrough);
        return this;
    }
    
    @SpecificImpl("setStrikethrough")
    @VersionRange(begin=1600)
    default Text setStrikethroughV1600(Boolean strikethrough)
    {
        this.setStyle(this.style().withStrikethroughV1600(strikethrough));
        return this;
    }
    
    default Boolean getObfuscated()
    {
        return this.style().getObfuscated();
    }
    
    Text setObfuscated(Boolean obfuscated);
    
    @SpecificImpl("setObfuscated")
    @VersionRange(end=1600)
    default Text setObfuscatedV_1600(Boolean obfuscated)
    {
        this.style().setObfuscated(obfuscated);
        return this;
    }
    
    @SpecificImpl("setObfuscated")
    @VersionRange(begin=1600)
    default Text setObfuscatedV1600(Boolean obfuscated)
    {
        this.setStyle(this.style().withObfuscatedV1600(obfuscated));
        return this;
    }
    
    default TextClickEvent getClickEvent()
    {
        return this.style().getClickEvent();
    }
    
    Text setClickEvent(TextClickEvent event);
    
    @SpecificImpl("setClickEvent")
    @VersionRange(end=1600)
    default Text setClickEventV_1600(TextClickEvent event)
    {
        this.style().setClickEvent(event);
        return this;
    }
    
    @SpecificImpl("setClickEvent")
    @VersionRange(begin=1600)
    default Text setClickEventV1600(TextClickEvent event)
    {
        this.setStyle(this.style().withClickEventV1600(event));
        return this;
    }
    
    default TextHoverEvent getHoverEvent()
    {
        return this.style().getHoverEvent();
    }
    
    Text setHoverEvent(TextHoverEvent event);
    
    @SpecificImpl("setHoverEvent")
    @VersionRange(end=1600)
    default Text setHoverEventV_1600(TextHoverEvent event)
    {
        this.style().setHoverEvent(event);
        return this;
    }
    
    @SpecificImpl("setHoverEvent")
    @VersionRange(begin=1600)
    default Text setHoverEventV1600(TextHoverEvent event)
    {
        this.setStyle(this.style().withHoverEventV1600(event));
        return this;
    }
    
    default String getInsertion()
    {
        return this.style().getInsertion();
    }
    
    Text setInsertion(String insertion);
    
    @SpecificImpl("setInsertion")
    @VersionRange(end=1600)
    default Text setInsertionV_1600(String insertion)
    {
        this.style().setInsertion(insertion);
        return this;
    }
    
    @SpecificImpl("setInsertion")
    @VersionRange(begin=1600)
    default Text setInsertionV1600(String insertion)
    {
        this.setStyle(this.style().withInsertionV1600(insertion));
        return this;
    }
    
    @WrapMinecraftMethod(@VersionName(name="getContent", begin=1900))
    TextContentV1900 getContentV1900();
    
    default String toLiteral()
    {
        String result = this.getLiteral();
        if(result!=null)
            return result;
        return "<unsupported>"; // FIXME
    }
    
    @WrapMinecraftInnerClass(outer=Text.class, name={@VersionName(name="Serializer", end=2003), @VersionName(name="Serialization", begin=2003)})
    interface Serializer extends WrapperObject
    {
        @WrapperCreator
        static Serializer create(Object wrapped)
        {
            return WrapperObject.create(Serializer.class, wrapped);
        }
        
        static Gson gson()
        {
            return Serializer.create(null).staticGson();
        }
        
        @WrapMinecraftFieldAccessor(@VersionName(name="GSON"))
        Gson staticGson();
        
        static JsonElement encode(Text text)
        {
            return Serializer.create(null).staticEncode(text);
        }
        
        JsonElement staticEncode(Text text);
        
        @SpecificImpl("staticEncode")
        @VersionRange(end=1400)
        default JsonElement staticEncodeV_1400(Text text)
        {
            return gson().toJsonTree(text.getWrapped());
        }
        
        @SpecificImpl("staticEncode")
        @WrapMinecraftMethod(@VersionName(name="toJsonTree", begin=1400, end=2005))
        JsonElement staticEncodeV1400_2005(Text text);
        
        @WrapMinecraftMethod(@VersionName(name="toJson", begin=2005))
        JsonElement staticEncodeV2005(Text text, RegistryEntryLookupV1903.class_7874 registries);
        
        @SpecificImpl("staticEncode")
        @VersionRange(begin=2005)
        default JsonElement staticEncodeV2005(Text text)
        {
            return this.staticEncodeV2005(text, MinecraftServer.instance.getRegistriesV1602());
        }
        
        static Text decode(JsonElement json)
        {
            return Serializer.create(null).staticDecode(json);
        }
        
        Text staticDecode(JsonElement json);
        
        @SpecificImpl("staticDecode")
        @VersionRange(end=1400)
        default Text staticDecodeV_1400(JsonElement json)
        {
            return Text.create(gson().fromJson(json, Text.create(null).staticGetWrappedClass()));
        }
        
        @SpecificImpl("staticDecode")
        @WrapMinecraftMethod(@VersionName(name="fromJson", end=1600))
        Text staticDecodeV1600(JsonElement json);
        
        @SpecificImpl("staticDecode")
        @WrapMinecraftMethod(@VersionName(name="fromJson", begin=1600, end=2005))
        TextMutableV1600 staticDecodeV1600_2005(JsonElement json);
        
        @WrapMinecraftMethod(@VersionName(name="fromJson", begin=2005))
        TextMutableV1600 staticDecodeV2005(JsonElement json, RegistryEntryLookupV1903.class_7874 registries);
        
        @SpecificImpl("staticDecode")
        @VersionRange(begin=2005)
        default Text staticDecodeV2005(JsonElement json)
        {
            return this.staticDecodeV2005(json, MinecraftServer.instance.getRegistriesV1602());
        }
    }
}
