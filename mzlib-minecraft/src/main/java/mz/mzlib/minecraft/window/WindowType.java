package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.MinecraftPlatform;

public class WindowType
{
    public static WindowType GENERIC_9x1 = new WindowType("minecraft:chest*9", "generic_9x1", 9);
    public static WindowType GENERIC_9x2 = new WindowType("minecraft:chest*18", "generic_9x2", 18);
    public static WindowType GENERIC_9x3 = new WindowType("minecraft:chest*27", "generic_9x3", 27);
    public static WindowType GENERIC_9x4 = new WindowType("minecraft:chest*36", "generic_9x4", 36);
    public static WindowType GENERIC_9x5 = new WindowType("minecraft:chest*45", "generic_9x5", 45);
    public static WindowType GENERIC_9x6 = new WindowType("minecraft:chest*54", "generic_9x6", 54);

    public static WindowType CRAFTING = new WindowType("minecraft:crafting_table", "crafting", 10);
    public static WindowType HOPPER = new WindowType("minecraft:hopper*5", "hopper", 5);
    public static WindowType ANVIL = new WindowType("minecraft:anvil", "anvil", 3);

    public static WindowType generic9x(int rows)
    {
        switch(rows)
        {
            case 1:
                return GENERIC_9x1;
            case 2:
                return GENERIC_9x2;
            case 3:
                return GENERIC_9x3;
            case 4:
                return GENERIC_9x4;
            case 5:
                return GENERIC_9x5;
            case 6:
                return GENERIC_9x6;
            default:
                throw new IllegalArgumentException(Integer.toString(rows));
        }
    }

    public String typeIdV_1400;
    public WindowTypeV1400 typeV1400;
    public int upperSize;
    public WindowType(String typeIdV_1400, WindowTypeV1400 typeV1400, int upperSize)
    {
        this.typeIdV_1400 = typeIdV_1400;
        this.typeV1400 = typeV1400;
        this.upperSize = upperSize;
    }

    public int getSize()
    {
        return this.upperSize;
    }

    public WindowType(String typeIdV_1400, String typeIdV1400, int upperSize)
    {
        this(
            typeIdV_1400, MinecraftPlatform.instance.getVersion() < 1400 ? null : WindowTypeV1400.fromId(typeIdV1400),
            upperSize
        );
    }
}
