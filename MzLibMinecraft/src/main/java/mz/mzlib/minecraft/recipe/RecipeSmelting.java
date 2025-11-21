package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.recipe.book.RecipeCookingCategoryV1903;

import java.util.Objects;
import java.util.function.Supplier;

public interface RecipeSmelting extends Recipe
{
    abstract class Builder
    {
        Identifier id;
        ItemStack result;
        float experience = 0.f;

        String groupV1300 = "";
        VanillaIngredient ingredientV1300;
        int cookingTimeV1300 = 200;
        RecipeCookingCategoryV1903 cookingCategoryV1903 = RECIPE_COOKING_CATEGORY_V1903;
        static RecipeCookingCategoryV1903 RECIPE_COOKING_CATEGORY_V1903 =
            MinecraftPlatform.instance.getVersion() < 1903 ? null : RecipeCookingCategoryV1903.MISC;

        public Builder id(Identifier value)
        {
            this.id = value;
            return this;
        }
        public Builder result(ItemStack value)
        {
            this.result = value;
            return this;
        }
        public Builder experience(float value)
        {
            this.experience = value;
            return this;
        }
        public Builder groupV1300(String value)
        {
            this.groupV1300 = value;
            return this;
        }
        public abstract Builder ingredient(ItemStack value);
        public Builder ingredientV1300(VanillaIngredient value)
        {
            this.ingredientV1300 = value;
            return this;
        }
        public Builder cookingTimeV1300(int value)
        {
            this.cookingTimeV1300 = value;
            return this;
        }
        public Builder cookingCategoryV1903(RecipeCookingCategoryV1903 value)
        {
            this.cookingCategoryV1903 = value;
            return this;
        }

        public abstract RecipeSmelting build();
        public RecipeRegistration buildRegistration()
        {
            return RecipeRegistration.of(this.getId(), this.build());
        }

        public Identifier getId()
        {
            return Objects.requireNonNull(this.id);
        }
        public ItemStack getResult()
        {
            return Objects.requireNonNull(this.result);
        }
        public VanillaIngredient getIngredientV1300()
        {
            return Objects.requireNonNull(this.ingredientV1300);
        }
    }

    Supplier<Builder> BUILDER_FACTORY =
        MinecraftPlatform.instance.getVersion() < 1300 ?
            RecipeSmeltingV_1300.Builder::new :
            RecipeVanillaSmeltingV1300.Builder::new;
    static Builder builder()
    {
        return BUILDER_FACTORY.get();
    }
}
