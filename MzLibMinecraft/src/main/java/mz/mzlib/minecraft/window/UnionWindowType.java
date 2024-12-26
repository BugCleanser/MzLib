package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.MinecraftPlatform;

public enum UnionWindowType
{
    GENERIC_9x1("minecraft:chest", getV1400OrNull("generic_9x1")),
    GENERIC_9x2("minecraft:chest", getV1400OrNull("generic_9x2")),
    GENERIC_9x3("minecraft:chest", getV1400OrNull("generic_9x3")),
    GENERIC_9x4("minecraft:chest", getV1400OrNull("generic_9x4")),
    GENERIC_9x5("minecraft:chest", getV1400OrNull("generic_9x5")),
    GENERIC_9x6("minecraft:chest", getV1400OrNull("generic_9x6")),
    
    CRAFTING("minecraft:crafting_table", getV1400OrNull("crafting")),
    HOPPER("minecraft:hopper", getV1400OrNull("hopper")),
    ANVIL("minecraft:anvil", getV1400OrNull("anvil")),
    
    ;
    
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
    
    public final String typeIdV_1400;
    public final WindowTypeV1400 typeV1400;
    UnionWindowType(String typeIdV_1400, WindowTypeV1400 typeV1400)
    {
        this.typeIdV_1400=typeIdV_1400;
        this.typeV1400=typeV1400;
    }
    
    public static WindowTypeV1400 getV1400OrNull(String id)
    {
        if(MinecraftPlatform.instance.getVersion()<1400)
            return null;
        return WindowTypeV1400.fromId(id);
    }
}
