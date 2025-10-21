package mz.mzlib.minecraft.text;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import mz.mzlib.minecraft.*;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.registry.entry.RegistryEntryLookupV1903;
import mz.mzlib.minecraft.serialization.JsonOpsV1300;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.module.MzModule;
import mz.mzlib.tester.SimpleTester;
import mz.mzlib.tester.TesterContext;
import mz.mzlib.util.*;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.Text"))
public interface Text extends WrapperObject
{
    WrapperFactory<Text> FACTORY = WrapperFactory.of(Text.class);
    @Deprecated
    @WrapperCreator
    static Text create(Object wrapped)
    {
        return WrapperObject.create(Text.class, wrapped);
    }
    
    static Text decode(JsonElement json)
    {
        return FACTORY.getStatic().staticDecode(json);
    }
    Text staticDecode(JsonElement json);
    @SpecificImpl("staticDecode")
    @VersionRange(end=2106)
    default Text staticDecodeV_2106(JsonElement json)
    {
        return SerializerV_2106.decode(json);
    }
    @SpecificImpl("staticDecode")
    @VersionRange(begin=2106)
    default Text staticDecodeV2106(JsonElement json)
    {
        Result<Option<Text>, String> result = TextCodecsV2003.codec().parse(JsonOpsV1300.instance(), json).toResult();
        for(String err: result.getError())
            throw new JsonParseException(err);
        return result.getValue().unwrap();
    }
    
    static Text decode(String json)
    {
        return decode(new Gson().fromJson(json, JsonElement.class));
    }
    
    JsonElement encode();
    @SpecificImpl("encode")
    @VersionRange(end=2106)
    default JsonElement encodeV_2106()
    {
        return SerializerV_2106.encode(this);
    }
    @SpecificImpl("encode")
    @VersionRange(begin=2106)
    default JsonElement encodeV2106()
    {
        Result<Option<JsonElement>, String> result = TextCodecsV2003.codec().encodeStart(JsonOpsV1300.instance(), this).toResult();
        for(String err: result.getError())
            throw new JsonParseException(err);
        return result.getValue().unwrap();
    }
    
    static Text fromLegacy(String legacy)
    {
        return literal(legacy); // FIXME
    }
    
    static Text literal(String str)
    {
        return TextLiteral.newInstance(str);
    }
    
    static Text translatable(String key, Object... args)
    {
        return TextTranslatable.newInstance(key, Arrays.asList(args));
    }
    
    @Deprecated
    static Text translatable(String key, Text[] args)
    {
        return translatable(key, (Object[]) args);
    }
    
    static Text keybindV1200(String key)
    {
        return TextKeybindV1200.newInstance(key);
    }
    
    static Text score(String name, String objective)
    {
        return TextScore.newInstance(name, objective);
    }
    
    static Text selector(String selector)
    {
        return TextSelector.newInstance(selector);
    }
    
    static Text objectAtlasV2109(Identifier atlas, Identifier sprite)
    {
        return TextObjectV2109.atlas(atlas, sprite);
    }
    static Text objectPlayerV2109(GameProfile.Description gameProfile, boolean hat)
    {
        return TextObjectV2109.player(gameProfile, hat);
    }
    
    @Deprecated
    default String getLiteral()
    {
        if(this.getType()!=Type.LITERAL)
            return null;
        return this.castTo(TextLiteral.FACTORY).getLiteral();
    }
    
    @Deprecated
    default String getTranslatableKey()
    {
        if(this.getType()!=Type.TRANSLATABLE)
            return null;
        return this.castTo(TextTranslatable.FACTORY).getKey();
    }
    
    @Deprecated
    default Text[] getTranslatableArgs()
    {
        if(this.getType()!=Type.TRANSLATABLE)
            return null;
        return this.castTo(TextTranslatable.FACTORY).getArgs().stream().map(a->
        {
            if(a instanceof Text)
                return (Text) a;
            return TextLiteral.newInstance(Objects.toString(a));
        }).toArray(Text[]::new);
    }
    
    @Deprecated
    @VersionRange(begin=1200)
    default String getKeybindV1200()
    {
        if(this.getType()!=Type.KEYBIND_V1200)
            return null;
        return this.castTo(TextKeybindV1200.FACTORY).getKey();
    }
    
    @WrapMinecraftMethod(@VersionName(name="getStyle"))
    TextStyle getStyle();
    
    void setStyle(TextStyle style);
    
    @SpecificImpl("setStyle")
    @VersionRange(end=1900)
    default void setStyleV_1900(TextStyle style)
    {
        this.castTo(AbstractTextV_1900.FACTORY).setStyleV_1900(style);
    }
    
    @SpecificImpl("setStyle")
    @VersionRange(begin=1900)
    default void setStyleV1900(TextStyle style)
    {
        this.castTo(TextMutableV1600.FACTORY).setStyleV1900(style);
    }
    
    default TextStyle style()
    {
        TextStyle result = this.getStyle();
        if(!result.isPresent())
            this.setStyle(result = TextStyle.empty());
        return result;
    }
    
    @WrapMinecraftMethod(@VersionName(name="getSiblings"))
    List<Object> getExtra0();
    
    default List<Text> getExtra()
    {
        List<Object> result = this.getExtra0();
        if(result==null)
            return null;
        return new ListProxy<>(result, InvertibleFunction.wrapper(Text.FACTORY));
    }
    
    Text setExtra(List<Text> value);
    
    @SpecificImpl("setExtra")
    @VersionRange(end=1900)
    default Text setExtraV_1900(List<Text> value)
    {
        return this.castTo(AbstractTextV_1900.FACTORY).setExtraV_1900(value);
    }
    
    @SpecificImpl("setExtra")
    @VersionRange(begin=1900)
    default Text setExtraV1900(List<Text> value)
    {
        return this.castTo(TextMutableV1600.FACTORY).setExtraV1900(value);
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
    
    default TextColor getColor()
    {
        return this.getStyle().getColor();
    }
    
    Text setColor(TextColor value);
    
    @SpecificImpl("setColor")
    @VersionRange(end=1600)
    default Text setColorV_1600(TextColor value)
    {
        this.style().setColorV_1600(value);
        return this;
    }
    
    @SpecificImpl("setColor")
    @VersionRange(begin=1600)
    default Text setColorV1600(TextColor value)
    {
        this.setStyle(this.style().withColorV1600(value));
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
        this.style().setBoldV_1600(bold);
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
        this.style().setItalicV_1600(italic);
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
        this.style().setUnderlinedV_1600(underlined);
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
        this.style().setStrikethroughV_1600(strikethrough);
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
        this.style().setObfuscatedV_1600(obfuscated);
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
        this.style().setClickEventV_1600(event);
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
        this.style().setHoverEventV_1600(event);
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
        this.style().setInsertionV_1600(insertion);
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
    
    default String toLegacy()
    {
        return this.toLegacy(TextStyle.empty());
    }
    default String toLegacy(TextStyle parentStyle)
    {
        TextStyle withParent = this.getStyle().withParent(parentStyle);
        String literal;
        switch(this.getType())
        {
            case LITERAL:
                literal = this.as(TextLiteral.FACTORY).getLiteral();
                break;
            case TRANSLATABLE:
                TextTranslatable textTranslatable = this.as(TextTranslatable.FACTORY);
                literal = textTranslatable.getKey()+'['+textTranslatable.getArgs().stream().map(a->a instanceof Text?((Text)a).toLegacy(withParent):a.toString()).collect(Collectors.joining(","))+']';
                break;
            case SCORE:
                TextScore textScore = this.as(TextScore.FACTORY);
                literal = textScore.getName()+'['+textScore.getObjective()+']';
                break;
            case SELECTOR:
                TextSelector textSelector = this.as(TextSelector.FACTORY);
                literal = textSelector.getSelector();
                if(MinecraftPlatform.instance.getVersion()>=1700)
                    for(Text separator: textSelector.getSeparatorV1700())
                    {
                        //noinspection StringConcatenationInLoop
                        literal += '[' + separator.toLegacy(parentStyle) + ']';
                    }
                break;
            case NBT_V1400:
                literal = "<nbt>"; // TODO
                break;
            case KEYBIND_V1200:
                literal = this.as(TextKeybindV1200.FACTORY).getKey();
                break;
            case OBJECT_V2109:
                literal = "<2109>"; // TODO
                break;
            default:
                literal = "<unknown>";
                break;
        }
        return withParent.toLegacy()+literal;
    }
    
    enum Type
    {
        UNKNOWN,
        LITERAL,
        TRANSLATABLE,
        SCORE,
        SELECTOR,
        KEYBIND_V1200,
        NBT_V1400,
        OBJECT_V2109
    }
    
    Type getType();
    @SpecificImpl("getType")
    @VersionRange(end=1200)
    default Type getTypeV_1200()
    {
        if(this.is(TextLiteral.FACTORY))
            return Type.LITERAL;
        if(this.is(TextTranslatable.FACTORY))
            return Type.TRANSLATABLE;
        if(this.is(TextScore.FACTORY))
            return Type.SCORE;
        if(this.is(TextSelector.FACTORY))
            return Type.SELECTOR;
        return Type.UNKNOWN;
    }
    @SpecificImpl("getType")
    @VersionRange(begin=1200, end=1400)
    default Type getTypeV1200_1400()
    {
        if(this.is(TextKeybindV1200.FACTORY))
            return Type.KEYBIND_V1200;
        return this.getTypeV_1200();
    }
    @SpecificImpl("getType")
    @VersionRange(begin=1400, end=1900)
    default Type getTypeV1400_1900()
    {
        if(this.is(TextNbtV1400.FACTORY))
            return Type.NBT_V1400;
        return this.getTypeV1200_1400();
    }
    @SpecificImpl("getType")
    @VersionRange(begin=1900, end=2109)
    default Type getTypeV1900_2109()
    {
        TextContentV1900 content = this.getContentV1900();
        if(content.is(TextContentLiteralV1900.FACTORY))
            return Type.LITERAL;
        if(content.is(TextContentTranslatableV1900.FACTORY))
            return Type.TRANSLATABLE;
        if(content.is(TextContentScoreV1900.FACTORY))
            return Type.SCORE;
        if(content.is(TextContentSelectorV1900.FACTORY))
            return Type.SELECTOR;
        if(content.is(TextContentKeybindV1900.FACTORY))
            return Type.KEYBIND_V1200;
        if(content.is(TextContentNbtV1900.FACTORY))
            return Type.NBT_V1400;
        return Type.UNKNOWN;
    }
    @SpecificImpl("getType")
    @VersionRange(begin=2109)
    default Type getTypeV2109()
    {
        TextContentV1900 content = this.getContentV1900();
        if(content.is(TextContentObjectV2109.FACTORY))
            return Type.OBJECT_V2109;
        return this.getTypeV1900_2109();
    }
    
    class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            SimpleTester.Builder<TesterContext> testBuilder = SimpleTester.Builder.common().setName(Text.class.getName()).setMinLevel(1);
            Function<Text, Text> codec = t->Text.decode(t.encode());
            Supplier<String> randomString = ()->Objects.toString(ThreadLocalRandom.current().nextLong());
            this.register(testBuilder.setFunction(context->
            {
                String literal = randomString.get();
                TextLiteral text = codec.apply(TextLiteral.newInstance(literal)).as(TextLiteral.FACTORY);
                if(text.getType()!=Type.LITERAL)
                    throw new AssertionError("literal type");
                if(!text.getLiteral().equals(literal))
                    throw new AssertionError("literal content");
            }).build());
            this.register(testBuilder.setFunction(context->
            {
                String key = randomString.get();
                String fallbackV1904 = randomString.get();
                int arg0 = ThreadLocalRandom.current().nextInt();
                TextLiteral arg1 = TextLiteral.newInstance(randomString.get());
                TextTranslatable text = codec.apply(MinecraftPlatform.instance.getVersion()<1904 ? TextTranslatable.newInstance(key, arg0, arg1) : TextTranslatable.newInstanceV1904(key, fallbackV1904, arg0, arg1)).as(TextTranslatable.FACTORY);
                if(text.getType()!=Type.TRANSLATABLE)
                    throw new AssertionError("translatable type");
                if(!text.getKey().equals(key))
                    throw new AssertionError("translatable key");
                if(!text.getArgs().get(0).toString().equals(Integer.toString(arg0)))
                    throw new AssertionError("translatable arg0");
                if(!text.getArgs().get(1).equals(arg1.getLiteral()) && !((Text)text.getArgs().get(1)).as(TextLiteral.FACTORY).getLiteral().equals(arg1.getLiteral()))
                    throw new AssertionError("translatable arg1");
                if(MinecraftPlatform.instance.getVersion()>=1904)
                {
                    if(!text.getFallbackV1904().equals(fallbackV1904))
                        throw new AssertionError("translatable fallback");
                }
            }).build());
            this.register(testBuilder.setFunction(context->
            {
                String name = randomString.get();
                String objective = randomString.get();
                TextScore text = codec.apply(TextScore.newInstance(name, objective)).as(TextScore.FACTORY);
                if(text.getType()!=Type.SCORE)
                    throw new AssertionError("score type");
                if(!text.getName().equals(name))
                    throw new AssertionError("score name");
                if(!text.getObjective().equals(objective))
                    throw new AssertionError("score objective");
            }).build());
            this.register(testBuilder.setFunction(context->
            {
                String selector = "@s";
                TextLiteral separator = TextLiteral.newInstance(randomString.get());
                TextSelector text = codec.apply(MinecraftPlatform.instance.getVersion()<1700 ? TextSelector.newInstance(selector) : TextSelector.newInstanceV1700(selector, Option.some(separator))).as(TextSelector.FACTORY);
                if(text.getType()!=Type.SELECTOR)
                    throw new AssertionError("selector type");
                if(!text.getSelector().equals(selector))
                    throw new AssertionError("selector selector");
                if(MinecraftPlatform.instance.getVersion()>=1700)
                {
                    if(!text.getSeparatorV1700().unwrap().as(TextLiteral.FACTORY).getLiteral().equals(separator.getLiteral()))
                        throw new AssertionError("selector separator");
                }
            }).build());
            if(MinecraftPlatform.instance.getVersion()>=1200)
                this.register(testBuilder.setFunction(context->
                {
                    String key = randomString.get();
                    TextKeybindV1200 text = codec.apply(TextKeybindV1200.newInstance(key)).as(TextKeybindV1200.FACTORY);
                    if(text.getType() != Type.KEYBIND_V1200)
                        throw new AssertionError("keybind type");
                    if(!text.getKey().equals(key))
                        throw new AssertionError("keybind key");
                }).build());
            if(MinecraftPlatform.instance.getVersion()>=1400)
                this.register(testBuilder.setFunction(context->
                {
                    JsonObject json = new JsonObject();
                    json.addProperty("type", "nbt");
                    json.addProperty("nbt", randomString.get());
                    json.addProperty("entity", randomString.get());
                    TextNbtV1400 text = codec.apply(Text.decode(json)).as(TextNbtV1400.FACTORY);
                    if(text.getType() != Type.NBT_V1400)
                        throw new AssertionError("nbt type");
                    // TODO
                }).build());
            if(MinecraftPlatform.instance.getVersion()>=2109)
                this.register(testBuilder.setFunction(context->
                {
                    TextObjectV2109 text = codec.apply(TextObjectV2109.atlas(Identifier.minecraft(randomString.get()), Identifier.minecraft(randomString.get()))).as(TextObjectV2109.FACTORY);
                    if(text.getType() != Type.OBJECT_V2109)
                        throw new AssertionError("object type");
                    if(text.getObjectType() != TextObjectV2109.Type.ATLAS)
                        throw new AssertionError("object atlas type");
                    text = codec.apply(TextObjectV2109.player(GameProfile.Description.texturesUrl("http://textures.minecraft.net/texture/dddacc418df7e30db188be7f3865495b2c8f7c9963bd9e1b9ed8d28d45cf3460"), true)).as(TextObjectV2109.FACTORY);
                    if(text.getType() != Type.OBJECT_V2109)
                        throw new AssertionError("object type");
                    if(text.getObjectType() != TextObjectV2109.Type.PLAYER)
                        throw new AssertionError("object player type");
                    // TODO
                }).build());
        }
    }
    
    @VersionRange(end=2106)
    @WrapMinecraftInnerClass(outer=Text.class, name={@VersionName(name="Serializer", end=2003), @VersionName(name="Serialization", begin=2003)})
    interface SerializerV_2106 extends WrapperObject
    {
        WrapperFactory<SerializerV_2106> FACTORY = WrapperFactory.of(SerializerV_2106.class);
        @Deprecated
        @WrapperCreator
        static SerializerV_2106 create(Object wrapped)
        {
            return WrapperObject.create(SerializerV_2106.class, wrapped);
        }
        
        static Gson gson()
        {
            return SerializerV_2106.create(null).staticGson();
        }
        
        @WrapMinecraftFieldAccessor(@VersionName(name="GSON"))
        Gson staticGson();
        
        static JsonElement encode(Text text)
        {
            return SerializerV_2106.create(null).staticEncode(text);
        }
        
        JsonElement staticEncode(Text text);
        
        @SpecificImpl("staticEncode")
        @VersionRange(end=1400)
        default JsonElement staticEncodeV_1400(Text text)
        {
            return gson().toJsonTree(text.getWrapped());
        }
        
        @SpecificImpl("staticEncode")
        @VersionRange(begin=1400, end=2005)
        @WrapMinecraftMethod(@VersionName(name="toJsonTree"))
        JsonElement staticEncodeV1400_2005(Text text);
        
        @VersionRange(begin=2005)
        @WrapMinecraftMethod(@VersionName(name="toJson"))
        JsonElement staticEncodeV2005(Text text, RegistryEntryLookupV1903.class_7874 registries);
        
        @SpecificImpl("staticEncode")
        @VersionRange(begin=2005)
        default JsonElement staticEncodeV2005(Text text)
        {
            return this.staticEncodeV2005(text, MinecraftServer.instance.getRegistriesV1602());
        }
        
        static Text decode(JsonElement json)
        {
            return SerializerV_2106.create(null).staticDecode(json);
        }
        
        Text staticDecode(JsonElement json);
        
        @SpecificImpl("staticDecode")
        @VersionRange(end=1300)
        default Text staticDecodeV_1300(JsonElement json)
        {
            return Text.create(gson().fromJson(json, Text.create(null).staticGetWrappedClass()));
        }
        
        @SpecificImpl("staticDecode")
        @VersionRange(begin=1300, end=1600)
        @WrapMinecraftMethod({@VersionName(name="method_20179", end=1400), @VersionName(name="fromJson", begin=1400)})
        Text staticDecodeV1300_1600(JsonElement json);
        
        @SpecificImpl("staticDecode")
        @VersionRange(begin=1600, end=2005)
        @WrapMinecraftMethod(@VersionName(name="fromJson"))
        TextMutableV1600 staticDecodeV1600_2005(JsonElement json);
        
        @VersionRange(begin=2005)
        @WrapMinecraftMethod(@VersionName(name="fromJson"))
        TextMutableV1600 staticDecodeV2005(JsonElement json, RegistryEntryLookupV1903.class_7874 registries);
        
        @SpecificImpl("staticDecode")
        @VersionRange(begin=2005)
        default Text staticDecodeV2005(JsonElement json)
        {
            return this.staticDecodeV2005(json, MinecraftServer.instance.getRegistriesV1602());
        }
    }
    
    /**
     * Use in js
     */
    @Deprecated
    default Text set(Map<String, Object> prop)
    {
        for(Map.Entry<String, Object> e: prop.entrySet())
        {
            switch(e.getKey())
            {
                case "color":
                    if(e.getValue() instanceof String)
                        this.setColor(new TextColor(TextFormatLegacy.fromName((String)e.getValue()))); // TODO hex
                    else
                        this.setColor((TextColor)JsUtil.toJvm(e.getValue()));
                    break;
                case "bold":
                    this.setBold((Boolean)JsUtil.toJvm(e.getValue()));
                    break;
                case "italic":
                    this.setItalic((Boolean)JsUtil.toJvm(e.getValue()));
                    break;
                case "underlined":
                    this.setUnderlined((Boolean)JsUtil.toJvm(e.getValue()));
                    break;
                case "strikethrough":
                    this.setStrikethrough((Boolean)JsUtil.toJvm(e.getValue()));
                    break;
                case "obfuscated":
                    this.setObfuscated((Boolean)JsUtil.toJvm(e.getValue()));
                    break;
                case "clickEvent":
                    this.setClickEvent((TextClickEvent)JsUtil.toJvm(e.getValue())); // FIXME
                    break;
                case "hoverEvent":
                    this.setHoverEvent((TextHoverEvent)JsUtil.toJvm(e.getValue())); // FIXME
                    break;
                case "insertion":
                    this.setInsertion((String)JsUtil.toJvm(e.getValue()));
                    break;
                case "extra":
                    this.setExtra(RuntimeUtil.cast(JsUtil.toJvm(e.getValue())));
                    break;
                default:
                    throw new IllegalArgumentException(prop.toString());
            }
        }
        return this;
    }
    /**
     * Use in js
     */
    @Deprecated
    static Text literal(String value, Map<String, Object> prop)
    {
        return literal(value).set(prop);
    }
    /**
     * Use in js
     */
    @Deprecated
    static Text translatable(String key, Map<String, Object> prop)
    {
        return translatable(key).set(prop);
    }
    /**
     * Use in js
     */
    @Deprecated
    static Text translatable(String key, Text[] args, Map<String, Object> prop)
    {
        return translatable(key, args).set(prop);
    }
    /**
     * Use in js
     */
    @Deprecated
    static Text score(Void todo, Map<String, Object> prop)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    /**
     * Use in js
     */
    @Deprecated
    static Text selector(String pattern, Map<String, Object> prop)
    {
        return selector(pattern).set(prop);
    }
    /**
     * Use in js
     */
    @Deprecated
    static Text keybindV1200(String key, Map<String, Object> prop)
    {
        return keybindV1200(key).set(prop);
    }
}
