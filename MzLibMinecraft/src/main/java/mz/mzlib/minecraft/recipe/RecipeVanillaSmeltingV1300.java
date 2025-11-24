package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.recipe.book.RecipeCookingCategoryV1903;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 1300)
@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.class_3584", end = 1400),
        @VersionName(name = "net.minecraft.recipe.SmeltingRecipe", begin = 1400)
    }
)
public interface RecipeVanillaSmeltingV1300 extends RecipeVanilla, RecipeSmelting
{
    WrapperFactory<RecipeVanillaSmeltingV1300> FACTORY = WrapperFactory.of(RecipeVanillaSmeltingV1300.class);

    static RecipeVanillaSmeltingV1300 of(Builder builder)
    {
        return FACTORY.getStatic().static$of(builder);
    }

    class Builder extends RecipeSmelting.Builder
    {
        @Override
        public Builder ingredient(ItemStack value)
        {
            return (Builder) this.ingredientV1300(VanillaIngredient.of(value));
        }

        @Override
        public RecipeVanillaSmeltingV1300 build()
        {
            return RecipeVanillaSmeltingV1300.of(this);
        }
    }

    RecipeVanillaSmeltingV1300 static$of(Builder builder);

    @SpecificImpl("static$of")
    @VersionRange(end = 1903)
    default RecipeVanillaSmeltingV1300 static$ofV_1903(Builder builder)
    {
        return this.static$ofV_1903(
            builder.getId(), builder.groupV1300, builder.getIngredientV1300(), builder.getResult(), builder.experience,
            builder.cookingTimeV1300
        );
    }
    @VersionRange(end = 1903)
    @WrapConstructor
    RecipeVanillaSmeltingV1300 static$ofV_1903(
        Identifier id,
        String group,
        VanillaIngredient ingredient,
        ItemStack result,
        float experience,
        int cookingTime);

    @SpecificImpl("static$of")
    @VersionRange(begin = 1903, end = 2002)
    default RecipeVanillaSmeltingV1300 static$ofV1903_2002(Builder builder)
    {
        return this.static$ofV1903_2002(
            builder.getId(), builder.groupV1300, builder.cookingCategoryV1903,
            builder.getIngredientV1300(), builder.getResult(), builder.experience, builder.cookingTimeV1300
        );
    }
    @VersionRange(begin = 1903, end = 2002)
    @WrapConstructor
    RecipeVanillaSmeltingV1300 static$ofV1903_2002(
        Identifier id,
        String group,
        RecipeCookingCategoryV1903 category,
        VanillaIngredient ingredient,
        ItemStack result,
        float experience,
        int cookingTime);

    @SpecificImpl("static$of")
    @VersionRange(begin = 2002)
    default RecipeVanillaSmeltingV1300 static$ofV2002(Builder builder)
    {
        return this.static$ofV2002(
            builder.groupV1300, builder.cookingCategoryV1903,
            builder.getIngredientV1300(), builder.getResult(), builder.experience, builder.cookingTimeV1300
        );
    }
    @VersionRange(begin = 2002)
    @WrapConstructor
    RecipeVanillaSmeltingV1300 static$ofV2002(
        String group,
        RecipeCookingCategoryV1903 category,
        VanillaIngredient ingredient,
        ItemStack result,
        float experience,
        int cookingTime);
}
