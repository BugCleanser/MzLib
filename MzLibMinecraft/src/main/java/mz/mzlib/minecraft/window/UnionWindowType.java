package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.MinecraftPlatform;

public class UnionWindowType
{
    public static UnionWindowType GENERIC_9x1 = new UnionWindowType("minecraft:chest", "generic_9x1", 9);
    public static UnionWindowType GENERIC_9x2 = new UnionWindowType("minecraft:chest", "generic_9x2", 18);
    public static UnionWindowType GENERIC_9x3 = new UnionWindowType("minecraft:chest", "generic_9x3", 27);
    public static UnionWindowType GENERIC_9x4 = new UnionWindowType("minecraft:chest", "generic_9x4", 36);
    public static UnionWindowType GENERIC_9x5 = new UnionWindowType("minecraft:chest", "generic_9x5", 45);
    public static UnionWindowType GENERIC_9x6 = new UnionWindowType("minecraft:chest", "generic_9x6", 54);
    
    public static UnionWindowType CRAFTING = new UnionWindowType("minecraft:crafting_table", "crafting", 10);
    public static UnionWindowType HOPPER = new UnionWindowType("minecraft:hopper", "hopper", 5);
    public static UnionWindowType ANVIL = new UnionWindowType("minecraft:anvil", "anvil", 3);
    
    public static UnionWindowType generic9x(int rows)
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
    public UnionWindowType(String typeIdPrefixV_1400, WindowTypeV1400 typeV1400, int upperSize)
    {
        this.typeIdV_1400 = typeIdPrefixV_1400+"*"+upperSize;
        this.typeV1400 = typeV1400;
        this.upperSize = upperSize;
    }
    
    public UnionWindowType(String typeIdV_1400, String typeIdV1400, int upperSize)
    {
        this(typeIdV_1400, MinecraftPlatform.instance.getVersion()<1400 ? null : WindowTypeV1400.fromId(typeIdV1400), upperSize);
    }
}
