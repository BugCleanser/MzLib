package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftPlatform;

public interface RecipeType
{
    RecipeType CRAFTING = MinecraftPlatform.instance.getVersion() < 1400 ? V_1400.CRAFTING : RecipeTypeV1400.CRAFTING;
    RecipeType FURNACE = MinecraftPlatform.instance.getVersion() < 1400 ? V_1400.FURNACE : RecipeTypeV1400.FURNACE;

    enum V_1400 implements RecipeType
    {
        CRAFTING,
        FURNACE,
    }
}
