package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import javax.annotation.Nullable;

/**
 * It's immutable since 1.16
 */
@WrapMinecraftClass({@VersionName(name="net.minecraft.text.Style", end=1400), @VersionName(name="net.minecraft.network.chat.Style", begin=1400, end=1403), @VersionName(name="net.minecraft.text.Style", begin=1403)})
public interface TextStyle extends WrapperObject
{
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
    
    static TextStyle newInstanceV1600(TextColorV1600 color, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font)
    {
        return create(null).staticNewInstanceV1600(color, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font);
    }
    @WrapConstructor
    @VersionRange(begin=1600)
    TextStyle staticNewInstanceV1600(TextColorV1600 color, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, TextClickEvent clickEvent, TextHoverEvent hoverEvent, String insertion, Identifier font);
    
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
    
    @WrapMinecraftFieldAccessor(@VersionName(name="underlined"))
    Boolean getUnderlined();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="underlined"))
    void setUnderlined(Boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="strikethrough"))
    Boolean getStrikethrough();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="strikethrough"))
    void setStrikethrough(Boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="obfuscated"))
    Boolean getObfuscated();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="obfuscated"))
    void setObfuscated(Boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="font", begin=1600))
    Identifier getFontV1600();
    
    // TODO
    @WrapMinecraftFieldAccessor({@VersionName(name="formatting", end=1400), @VersionName(name="color", begin=1400, end=1600)})
    TextFormat getColorV_1600();
    
    @WrapMinecraftFieldAccessor({@VersionName(name="formatting", end=1400), @VersionName(name="color", begin=1400, end=1600)})
    void setColorV_1600(TextFormat value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="color", begin=1600))
    TextColorV1600 getColorV1600();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="color", begin=1600))
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
    
    @WrapMinecraftMethod(@VersionName(name="withColor", begin=1600))
    TextStyle withColorV1600(TextColorV1600 color);
    @WrapMinecraftMethod(@VersionName(name="withBold", begin=1600))
    TextStyle withBoldV1600(Boolean bold);
    @WrapMinecraftMethod(@VersionName(name="withItalic", begin=1600))
    TextStyle withItalicV1600(Boolean italic);
    default TextStyle withUnderlinedV1600(Boolean underlined)
    {
        return checkEmptyV1600(newInstanceV1600(this.getColorV1600(), this.getBold(), this.getItalic(), underlined, this.getStrikethrough(), this.getObfuscated(), this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
    }
    default TextStyle withStrikethroughV1600(Boolean strikethrough)
    {
        return checkEmptyV1600(newInstanceV1600(this.getColorV1600(), this.getBold(), this.getItalic(), this.getUnderlined(), strikethrough, this.getObfuscated(), this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
    }
    default TextStyle withObfuscatedV1600(Boolean obfuscated)
    {
        return checkEmptyV1600(newInstanceV1600(this.getColorV1600(), this.getBold(), this.getItalic(), this.getUnderlined(), this.getStrikethrough(), obfuscated, this.getClickEvent(), this.getHoverEvent(), this.getInsertion(), this.getFontV1600()));
    }
    @WrapMinecraftMethod(@VersionName(name="withClickEvent", begin=1600))
    TextStyle withClickEventV1600(TextClickEvent clickEvent);
    @WrapMinecraftMethod({@VersionName(name="setHoverEvent", begin=1600, end=1602), @VersionName(name="withHoverEvent", begin=1602)})
    TextStyle withHoverEventV1600(TextHoverEvent hoverEvent);
    @WrapMinecraftMethod(@VersionName(name="withInsertion", begin=1600))
    TextStyle withInsertionV1600(String insertion);
}
