package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
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
        return create(null).staticRootV_1600();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="ROOT", end=1600))
    TextStyle staticRootV_1600();
    
    static TextStyle empty()
    {
        return create(null).staticEmpty();
    }
    TextStyle staticEmpty();
    @SpecificImpl("staticEmpty")
    @VersionRange(end=1600)
    @WrapConstructor
    TextStyle staticNewInstanceV_1600();
    @SpecificImpl("staticEmpty")
    @WrapMinecraftFieldAccessor(@VersionName(name="EMPTY", begin=1600))
    TextStyle staticEmptyV1600();
    
    @VersionRange(begin=1600)
    static TextStyle newInstanceV1600(TextColorV1600 color, Integer shadowColorV2104, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font)
    {
        return create(null).staticNewInstanceV1600(color, shadowColorV2104, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font);
    }
    @VersionRange(begin=1600)
    TextStyle staticNewInstanceV1600(TextColorV1600 color, Integer shadowColorV2104, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font);
    @VersionRange(begin=1600, end=2104)
    @WrapConstructor
    TextStyle staticNewInstanceV1600_2104(TextColorV1600 color, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font);
    @VersionRange(begin=1600, end=2104)
    @SpecificImpl("staticNewInstanceV1600")
    default TextStyle staticNewInstanceV1600_2104(TextColorV1600 color, Integer shadowColorV2104, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font)
    {
        return this.staticNewInstanceV1600_2104(color, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font);
    }
    @VersionRange(begin=2104)
    @WrapConstructor
    TextStyle staticNewInstanceV2104(TextColorV1600 color, Integer shadowColorV2104, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font);
    
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
    void setBold(Boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="italic"))
    Boolean getItalic();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="italic"))
    void setItalic(Boolean value);
    
    @WrapMinecraftFieldAccessor({@VersionName(name="underline", end=1400), @VersionName(name="field_11851", begin=1400)})
    Boolean getUnderlined();
    
    @WrapMinecraftFieldAccessor({@VersionName(name="underline", end=1400), @VersionName(name="field_11851", begin=1400)})
    void setUnderlined(Boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="strikethrough"))
    Boolean getStrikethrough();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="strikethrough"))
    void setStrikethrough(Boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="obfuscated"))
    Boolean getObfuscated();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="obfuscated"))
    void setObfuscated(Boolean value);
    
    @VersionRange(begin=1600)
    @WrapMinecraftFieldAccessor(@VersionName(name="font"))
    Identifier getFontV1600();
    
    // TODO
    @VersionRange(end=1600)
    @WrapMinecraftFieldAccessor({@VersionName(name="formatting", end=1400), @VersionName(name="color", begin=1400)})
    TextFormatLegacy getColorV_1600();
    
    @VersionRange(end=1600)
    @WrapMinecraftFieldAccessor({@VersionName(name="formatting", end=1400), @VersionName(name="color", begin=1400)})
    void setColorV_1600(TextFormatLegacy value);
    
    @VersionRange(begin=1600)
    @WrapMinecraftFieldAccessor(@VersionName(name="color"))
    TextColorV1600 getColorV1600();
    
    @VersionRange(begin=1600)
    @WrapMinecraftFieldAccessor(@VersionName(name="color"))
    void setColorV1600(TextColorV1600 value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="insertion"))
    String getInsertion();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="insertion"))
    void setInsertion(String value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="hoverEvent"))
    TextHoverEvent getHoverEvent();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="hoverEvent"))
    void setHoverEvent(TextHoverEvent value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="clickEvent"))
    TextClickEvent getClickEvent();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="clickEvent"))
    void setClickEvent(TextClickEvent value);
    
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
        return checkEmptyV1600(newInstanceV1600(this.getColorV1600(), this.getShadowColor(), this.getBold(), this.getItalic(), underlined, this.getStrikethrough(), this.getObfuscated(), this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
    }
    @VersionRange(begin=1600)
    default TextStyle withStrikethroughV1600(Boolean strikethrough)
    {
        return checkEmptyV1600(newInstanceV1600(this.getColorV1600(), this.getShadowColor(), this.getBold(), this.getItalic(), this.getUnderlined(), strikethrough, this.getObfuscated(), this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
    }
    @VersionRange(begin=1600)
    default TextStyle withObfuscatedV1600(Boolean obfuscated)
    {
        return checkEmptyV1600(newInstanceV1600(this.getColorV1600(), this.getShadowColor(), this.getBold(), this.getItalic(), this.getUnderlined(), this.getStrikethrough(), obfuscated, this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
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
        return checkEmptyV1600(newInstanceV1600(this.getColorV1600(), shadowColor, this.getBold(), this.getItalic(), this.getUnderlined(), this.getStrikethrough(), this.getObfuscated(), this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
    }
}
