package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.util.Formatting"))
public interface TextFormatLegacy extends WrapperObject
{
    WrapperFactory<TextFormatLegacy> FACTORY = WrapperFactory.of(TextFormatLegacy.class);
    @Deprecated
    @WrapperCreator
    static TextFormatLegacy create(Object wrapped)
    {
        return WrapperObject.create(TextFormatLegacy.class, wrapped);
    }
    
    TextFormatLegacy BLACK = fromName("BLACK");
    TextFormatLegacy DARK_BLUE = fromName("DARK_BLUE");
    TextFormatLegacy DARK_GREEN = fromName("DARK_GREEN");
    TextFormatLegacy DARK_AQUA = fromName("DARK_AQUA");
    TextFormatLegacy DARK_RED = fromName("DARK_RED");
    TextFormatLegacy DARK_PURPLE = fromName("DARK_PURPLE");
    TextFormatLegacy GOLD = fromName("GOLD");
    TextFormatLegacy GRAY = fromName("GRAY");
    TextFormatLegacy DARK_GRAY = fromName("DARK_GRAY");
    TextFormatLegacy BLUE = fromName("BLUE");
    TextFormatLegacy GREEN = fromName("GREEN");
    TextFormatLegacy AQUA = fromName("AQUA");
    TextFormatLegacy RED = fromName("RED");
    TextFormatLegacy LIGHT_PURPLE = fromName("LIGHT_PURPLE");
    TextFormatLegacy YELLOW = fromName("YELLOW");
    TextFormatLegacy WHITE = fromName("WHITE");
    TextFormatLegacy OBFUSCATED = fromName("OBFUSCATED");
    TextFormatLegacy BOLD = fromName("BOLD");
    TextFormatLegacy STRIKETHROUGH = fromName("STRIKETHROUGH");
    TextFormatLegacy UNDERLINE = fromName("UNDERLINE");
    TextFormatLegacy ITALIC = fromName("ITALIC");
    TextFormatLegacy RESET = fromName("RESET");
    
    @WrapMinecraftFieldAccessor(@VersionName(name="code"))
    char getCode();
    
    static TextFormatLegacy fromName(String name)
    {
        return create(null).staticFromName(name);
    }
    
    @WrapMinecraftMethod(@VersionName(name="byName"))
    TextFormatLegacy staticFromName(String name);
}
