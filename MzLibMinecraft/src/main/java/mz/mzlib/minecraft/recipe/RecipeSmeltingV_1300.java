package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.item.ItemStack;

import java.util.Objects;

public class RecipeSmeltingV_1300 implements RecipeSmelting
{
    ItemStack ingredient;
    ItemStack result;
    float experience;

    public RecipeSmeltingV_1300(ItemStack ingredient, ItemStack result, float experience)
    {
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
    }

    public ItemStack getIngredient()
    {
        return this.ingredient;
    }
    public ItemStack getResult()
    {
        return this.result;
    }
    public float getExperience()
    {
        return this.experience;
    }

    @Override
    public RecipeType getType()
    {
        return RecipeType.SMELTING;
    }

    public static class Builder extends RecipeSmelting.Builder
    {
        ItemStack ingredient;

        @Override
        public Builder ingredient(ItemStack value)
        {
            this.ingredient = value;
            return this;
        }

        public ItemStack getIngredient()
        {
            return Objects.requireNonNull(this.ingredient);
        }

        @Override
        public RecipeSmeltingV_1300 build()
        {
            return new RecipeSmeltingV_1300(this.getIngredient(), this.getResult(), this.experience);
        }
    }
}
