package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.item.ItemStack;

import java.util.List;

public interface Recipe
{
    RecipeType getType();

    List<ItemStack> getIcons();
}
