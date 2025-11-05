package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.text.FontDescriptionV2109;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.*;

/**
 * It's immutable since 1.16
 */
@WrapMinecraftClass({@VersionName(name="net.minecraft.text.Style", end=1400), @VersionName(name="net.minecraft.network.chat.Style", begin=1400, end=1403), @VersionName(name="net.minecraft.text.Style", begin=1403)})
public interface TextStyle extends WrapperObject
{
    WrapperFactory<TextStyle> FACTORY = WrapperFactory.of(TextStyle.class);
    @Deprecated
    @WrapperCreator
    static TextStyle create(Object wrapped)
    {
        return WrapperObject.create(TextStyle.class, wrapped);
    }
    
    static TextStyle rootV_1600()
    {
        return FACTORY.getStatic().static$rootV_1600();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="ROOT", end=1600))
    TextStyle static$rootV_1600();
    
    static TextStyle empty()
    {
        return FACTORY.getStatic().static$empty();
    }
    TextStyle static$empty();
    @SpecificImpl("static$empty")
    @VersionRange(end=1600)
    @WrapConstructor
    TextStyle static$newInstanceV_1600();
    @SpecificImpl("static$empty")
    @WrapMinecraftFieldAccessor(@VersionName(name="EMPTY", begin=1600))
    TextStyle static$emptyV1600();
    
    @VersionRange(begin=1600)
    static TextStyle newInstanceV1600(TextColorV1600 color, Integer shadowColorV2104, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font)
    {
        return FACTORY.getStatic().static$newInstanceV1600(color, shadowColorV2104, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font);
    }
    @VersionRange(begin=1600)
    TextStyle static$newInstanceV1600(TextColorV1600 color, Integer shadowColorV2104, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font);
    @VersionRange(begin=1600, end=2104)
    @WrapConstructor
    TextStyle static$newInstanceV1600_2104(TextColorV1600 color, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font);
    @VersionRange(begin=1600, end=2104)
    @SpecificImpl("static$newInstanceV1600")
    default TextStyle static$newInstanceV1600_2104(TextColorV1600 color, Integer shadowColorV2104, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font)
    {
        return this.static$newInstanceV1600_2104(color, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font);
    }
    @SpecificImpl("static$newInstanceV1600")
    @VersionRange(begin=2104, end=2109)
    @WrapConstructor
    TextStyle static$newInstanceV2104_2109(TextColorV1600 color, Integer shadowColorV2104, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font);
    @SpecificImpl("static$newInstanceV1600")
    @VersionRange(begin=2109)
    default TextStyle static$newInstanceV2109(TextColorV1600 color, Integer shadowColorV2104, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font)
    {
        return this.static$newInstanceV2109(color, shadowColorV2104, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, Option.fromWrapper(font).map(FontDescriptionV2109.Resource::newInstance).unwrapOrGet(FontDescriptionV2109.Resource.FACTORY::getStatic));
    }
    @VersionRange(begin=2109)
    @WrapConstructor
    TextStyle static$newInstanceV2109(TextColorV1600 color, Integer shadowColorV2104, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, FontDescriptionV2109 font);
    
    Integer getShadowColor();
    @SpecificImpl("getShadowColor")
    @VersionRange(end=2104)
    default Integer getShadowColorV_2104()
    {
        return null;
    }
    @SpecificImpl("getShadowColor")
    @VersionRange(begin=2104)
    @WrapMinecraftFieldAccessor(@VersionName(name="shadowColor"))
    Integer getShadowColorV2104();
    
    @VersionRange(begin=1600)
    static TextStyle checkEmptyV1600(TextStyle style)
    {
        if(style.equals(empty()))
            return empty();
        return style;
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="bold"))
    Boolean getBold();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="bold"))
    void setBoldV_1600(Boolean value);
    @Deprecated
    default void setBold(Boolean value)
    {
        this.setBoldV_1600(value);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="italic"))
    Boolean getItalic();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="italic"))
    void setItalicV_1600(Boolean value);
    @Deprecated
    default void setItalic(Boolean value)
    {
        this.setItalicV_1600(value);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="underline", end=1400), @VersionName(name="field_11851", begin=1400)})
    Boolean getUnderlined();
    
    @WrapMinecraftFieldAccessor({@VersionName(name="underline", end=1400), @VersionName(name="field_11851", begin=1400)})
    void setUnderlinedV_1600(Boolean value);
    @Deprecated
    default void setUnderlined(Boolean value)
    {
        this.setUnderlinedV_1600(value);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="strikethrough"))
    Boolean getStrikethrough();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="strikethrough"))
    void setStrikethroughV_1600(Boolean value);
    @Deprecated
    default void setStrikethrough(Boolean value)
    {
        this.setStrikethroughV_1600(value);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="obfuscated"))
    Boolean getObfuscated();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="obfuscated"))
    void setObfuscatedV_1600(Boolean value);
    @Deprecated
    default void setObfuscated(Boolean value)
    {
        this.setObfuscatedV_1600(value);
    }
    
    @VersionRange(begin=1600)
    Identifier getFontV1600();
    @SpecificImpl("getFontV1600")
    @VersionRange(begin=1600, end=2109)
    @WrapMinecraftFieldAccessor(@VersionName(name="font"))
    Identifier getFontV1600_2109();
    @SpecificImpl("getFontV1600")
    @VersionRange(begin=2109)
    default Identifier getFontV2109()
    {
        for(FontDescriptionV2109 fontDescription: Option.fromWrapper(this.getFontDescriptionV2109()))
        {
            return fontDescription.castTo(FontDescriptionV2109.Resource.FACTORY).getId();
        }
        return Identifier.FACTORY.create(null);
    }
    @VersionRange(begin=2109)
    @WrapMinecraftFieldAccessor(@VersionName(name="font"))
    FontDescriptionV2109 getFontDescriptionV2109();
    
    TextColor getColor();
    @SpecificImpl("getColor")
    @VersionRange(end=1600)
    default TextColor getColorV_1600()
    {
        TextFormatLegacy result = this.getColor0V_1600();
        if(!result.isPresent())
            return null;
        return new TextColor(result);
    }
    
    @SpecificImpl("getColor")
    @VersionRange(begin=1600)
    default TextColor getColorV1600()
    {
        TextColorV1600 result = this.getColor0V1600();
        if(!result.isPresent())
            return null;
        return new TextColor(result);
    }
    @VersionRange(end=1600)
    @WrapMinecraftFieldAccessor({@VersionName(name="formatting", end=1400), @VersionName(name="color", begin=1400)})
    TextFormatLegacy getColor0V_1600();
    @VersionRange(begin=1600)
    @WrapMinecraftFieldAccessor(@VersionName(name="color"))
    TextColorV1600 getColor0V1600();
    
    @VersionRange(end=1600)
    default void setColorV_1600(TextColor value)
    {
        this.setColorV_1600(value!=null ? value.legacy : TextFormatLegacy.FACTORY.create(null));
    }
    @VersionRange(end=1600)
    @WrapMinecraftFieldAccessor({@VersionName(name="formatting", end=1400), @VersionName(name="color", begin=1400)})
    void setColorV_1600(TextFormatLegacy value);
    
    @Deprecated
    @VersionRange(begin=1600)
    @WrapMinecraftFieldAccessor(@VersionName(name="color"))
    void setColorV1600(TextColorV1600 value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="insertion"))
    String getInsertion();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="insertion"))
    void setInsertionV_1600(String value);
    @Deprecated
    default void setInsertion(String value)
    {
        this.setInsertionV_1600(value);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="hoverEvent"))
    TextHoverEvent getHoverEvent();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="hoverEvent"))
    void setHoverEventV_1600(TextHoverEvent value);
    @Deprecated
    default void setHoverEvent(TextHoverEvent value)
    {
        this.setHoverEventV_1600(value);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="clickEvent"))
    TextClickEvent getClickEvent();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="clickEvent"))
    void setClickEventV_1600(TextClickEvent value);
    @Deprecated
    default void setClickEvent(TextClickEvent value)
    {
        this.setClickEventV_1600(value);
    }
    
    default TextStyle withColorV1600(TextColor color)
    {
        return this.withColorV1600(color!=null ? color.v1600 : TextColorV1600.FACTORY.create(null));
    }
    @VersionRange(begin=1600)
    @WrapMinecraftMethod(@VersionName(name="withColor"))
    TextStyle withColorV1600(TextColorV1600 color);
    @VersionRange(begin=1600)
    @WrapMinecraftMethod(@VersionName(name="withBold"))
    TextStyle withBoldV1600(Boolean bold);
    @VersionRange(begin=1600)
    @WrapMinecraftMethod(@VersionName(name="withItalic"))
    TextStyle withItalicV1600(Boolean italic);
    @VersionRange(begin=1600)
    default TextStyle withUnderlinedV1600(Boolean underlined)
    {
        return checkEmptyV1600(newInstanceV1600(this.getColor0V1600(), this.getShadowColor(), this.getBold(), this.getItalic(), underlined, this.getStrikethrough(), this.getObfuscated(), this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
    }
    @VersionRange(begin=1600)
    default TextStyle withStrikethroughV1600(Boolean strikethrough)
    {
        return checkEmptyV1600(newInstanceV1600(this.getColor0V1600(), this.getShadowColor(), this.getBold(), this.getItalic(), this.getUnderlined(), strikethrough, this.getObfuscated(), this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
    }
    @VersionRange(begin=1600)
    default TextStyle withObfuscatedV1600(Boolean obfuscated)
    {
        return checkEmptyV1600(newInstanceV1600(this.getColor0V1600(), this.getShadowColor(), this.getBold(), this.getItalic(), this.getUnderlined(), this.getStrikethrough(), obfuscated, this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
    }
    @VersionRange(begin=1600)
    @WrapMinecraftMethod(@VersionName(name="withClickEvent"))
    TextStyle withClickEventV1600(TextClickEvent clickEvent);
    @VersionRange(begin=1600)
    @WrapMinecraftMethod({@VersionName(name="setHoverEvent", end=1602), @VersionName(name="withHoverEvent", begin=1602)})
    TextStyle withHoverEventV1600(TextHoverEvent hoverEvent);
    @VersionRange(begin=1600)
    @WrapMinecraftMethod(@VersionName(name="withInsertion"))
    TextStyle withInsertionV1600(String insertion);
    @VersionRange(begin=2104)
    default TextStyle withShadowColorV2104(Integer shadowColor)
    {
        return checkEmptyV1600(newInstanceV1600(this.getColor0V1600(), shadowColor, this.getBold(), this.getItalic(), this.getUnderlined(), this.getStrikethrough(), this.getObfuscated(), this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
    }
    
    TextStyle withParent(TextStyle parent);
    @SpecificImpl("withParent")
    @VersionRange(end=1600)
    default TextStyle withParentV_1600(TextStyle parent)
    {
        TextStyle result = empty();
        result.setColorV_1600(this.getColorV_1600()!=null ? this.getColorV_1600() : parent.getColorV_1600());
        result.setInsertionV_1600(this.getInsertion()!=null ? this.getInsertion() : parent.getInsertion());
        result.setHoverEventV_1600(this.getHoverEvent().isPresent() ? this.getHoverEvent() : parent.getHoverEvent());
        result.setClickEventV_1600(this.getClickEvent().isPresent() ? this.getClickEvent() : parent.getClickEvent());
        result.setObfuscatedV_1600(this.getObfuscated()!=null ? this.getObfuscated() : parent.getObfuscated());
        result.setBoldV_1600(this.getBold()!=null ? this.getBold() : parent.getBold());
        result.setStrikethroughV_1600(this.getStrikethrough()!=null ? this.getStrikethrough() : parent.getStrikethrough());
        result.setObfuscatedV_1600(this.getUnderlined()!=null ? this.getUnderlined() : parent.getUnderlined());
        result.setItalicV_1600(this.getItalic()!=null ? this.getItalic() : parent.getItalic());
        return result;
    }
    @SpecificImpl("withParent")
    @VersionRange(begin=1600)
    @WrapMinecraftMethod(@VersionName(name="withParent"))
    TextStyle withParentV1600(TextStyle parent);
    
    default String toLegacy()
    {
        StringBuilder sb = new StringBuilder("§r");
        for(TextFormatLegacy c: Option.fromNullable(this.getColor()).map(TextColor::getLegacy))
        {
            sb = new StringBuilder();
            sb.append('§');
            sb.append(c.getCode());
        }
        if(this.getObfuscated()==Boolean.TRUE)
            sb.append("§k");
        if(this.getBold()==Boolean.TRUE)
            sb.append("§l");
        if(this.getStrikethrough()==Boolean.TRUE)
            sb.append("§m");
        if(this.getUnderlined()==Boolean.TRUE)
            sb.append("§n");
        if(this.getItalic()==Boolean.TRUE)
            sb.append("§o");
        return sb.toString();
    }
}
