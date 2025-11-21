package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftPlatform;

public interface RecipeType
{
    RecipeType CRAFTING = MinecraftPlatform.instance.getVersion() < 1400 ? V_1400.CRAFTING : RecipeTypeV1400.CRAFTING;
    RecipeType SMELTING = MinecraftPlatform.instance.getVersion() < 1400 ? V_1400.SMELTING : RecipeTypeV1400.SMELTING;

    enum V_1400 implements RecipeType
    {
        CRAFTING,
        SMELTING,
    }
}
