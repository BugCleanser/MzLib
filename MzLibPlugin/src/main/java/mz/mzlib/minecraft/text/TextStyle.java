package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.Style", end=1400),
                @VersionName(name = "net.minecraft.network.chat.Style", begin=1400, end=1403),
                @VersionName(name = "net.minecraft.text.Style", begin=1403)
        })
public interface TextStyle extends WrapperObject
{
    @WrapperCreator
    static TextStyle create(Object wrapped)
    {
        return WrapperObject.create(TextStyle.class, wrapped);
    }

    @WrapConstructor
    TextStyle staticNewInstance();
    static TextStyle newInstance()
    {
        return create(null).staticNewInstance();
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "bold"))
    Boolean getBold();
    @WrapMinecraftFieldAccessor(@VersionName(name = "bold"))
    void setBold(Boolean value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "italic"))
    Boolean getItalic();
    @WrapMinecraftFieldAccessor(@VersionName(name = "italic"))
    void setItalic(Boolean value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "underline"))
    Boolean getUnderline();
    @WrapMinecraftFieldAccessor(@VersionName(name = "underline"))
    void setUnderline(Boolean value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "strikethrough"))
    Boolean getStrikethrough();
    @WrapMinecraftFieldAccessor(@VersionName(name = "strikethrough"))
    void setStrikethrough(Boolean value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "obfuscated"))
    Boolean getObfuscated();
    @WrapMinecraftFieldAccessor(@VersionName(name = "obfuscated"))
    void setObfuscated(Boolean value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "formatting"))
    TextFormat getColor();
    @WrapMinecraftFieldAccessor(@VersionName(name = "formatting"))
    void setColor(TextFormat value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "insertion"))
    String getInsertion();
    @WrapMinecraftFieldAccessor(@VersionName(name = "insertion"))
    void setInsertion(String value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "hoverEvent"))
    TextHoverEvent getHoverEvent();
    @WrapMinecraftFieldAccessor(@VersionName(name = "hoverEvent"))
    void setHoverEvent(TextHoverEvent value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "clickEvent"))
    TextClickEvent getClickEvent();
    @WrapMinecraftFieldAccessor(@VersionName(name = "clickEvent"))
    void setClickEvent(TextClickEvent value);
}
