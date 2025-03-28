package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.MinecraftPlatform;

public class TextColor
{
    public static TextColor BLACK = new TextColor(TextFormatLegacy.BLACK);
    public static TextColor DARK_BLUE = new TextColor(TextFormatLegacy.DARK_BLUE);
    public static TextColor DARK_GREEN = new TextColor(TextFormatLegacy.DARK_GREEN);
    public static TextColor DARK_AQUA = new TextColor(TextFormatLegacy.DARK_AQUA);
    public static TextColor DARK_RED = new TextColor(TextFormatLegacy.DARK_RED);
    public static TextColor DARK_PURPLE = new TextColor(TextFormatLegacy.DARK_PURPLE);
    public static TextColor GOLD = new TextColor(TextFormatLegacy.GOLD);
    public static TextColor GRAY = new TextColor(TextFormatLegacy.GRAY);
    public static TextColor DARK_GRAY = new TextColor(TextFormatLegacy.DARK_GRAY);
    public static TextColor BLUE = new TextColor(TextFormatLegacy.BLUE);
    public static TextColor GREEN = new TextColor(TextFormatLegacy.GREEN);
    public static TextColor AQUA = new TextColor(TextFormatLegacy.AQUA);
    public static TextColor RED = new TextColor(TextFormatLegacy.RED);
    public static TextColor LIGHT_PURPLE = new TextColor(TextFormatLegacy.LIGHT_PURPLE);
    public static TextColor YELLOW = new TextColor(TextFormatLegacy.YELLOW);
    public static TextColor WHITE = new TextColor(TextFormatLegacy.WHITE);
    
    public TextFormatLegacy legacy;
    public TextColorV1600 v1600;
    
    public TextColor(TextFormatLegacy legacy, TextColorV1600 v1600)
    {
        this.legacy = legacy;
        this.v1600 = v1600;
    }
    public TextColor(TextFormatLegacy legacy)
    {
        this(legacy, MinecraftPlatform.instance.getVersion()<1600?null:TextColorV1600.fromLegacy(legacy));
    }
    public TextColor(TextColorV1600 v1600)
    {
        this(null, v1600);
    }
    
    public static TextColor fromRgbV1600(int rgb)
    {
        return new TextColor(TextColorV1600.fromRgb(rgb));
    }
    
    public TextFormatLegacy getLegacy()
    {
        return this.legacy;
    }
    
    public char getCode()
    {
        return this.legacy.getCode();
    }
}
