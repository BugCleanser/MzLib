package mz.mzlib.minecraft.recipe.smelting;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.recipe.RecipeMojang;
import mz.mzlib.minecraft.recipe.IngredientVanilla;
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
public interface RecipeFurnaceV1300 extends RecipeMojang, RecipeFurnace
{
    WrapperFactory<RecipeFurnaceV1300> FACTORY = WrapperFactory.of(RecipeFurnaceV1300.class);

    static RecipeFurnaceV1300 of(Builder builder)
    {
        return FACTORY.getStatic().static$of(builder);
    }

    class Builder extends RecipeFurnace.Builder
    {
        @Override
        public Builder ingredient(ItemStack value)
        {
            return (Builder) this.ingredientV1300(IngredientVanilla.of(value));
        }

        @Override
        public RecipeFurnaceV1300 build()
        {
            return RecipeFurnaceV1300.of(this);
        }
    }

    RecipeFurnaceV1300 static$of(Builder builder);

    @SpecificImpl("static$of")
    @VersionRange(end = 1903)
    default RecipeFurnaceV1300 static$ofV_1903(Builder builder)
    {
        return this.static$ofV_1903(
            builder.getId(), builder.groupV1300, builder.getIngredientV1300(), builder.getResult(), builder.experience,
            builder.cookingTimeV1300
        );
    }
    @VersionRange(end = 1903)
    @WrapConstructor
    RecipeFurnaceV1300 static$ofV_1903(
        Identifier id,
        String group,
        IngredientVanilla ingredient,
        ItemStack result,
        float experience,
        int cookingTime);

    @SpecificImpl("static$of")
    @VersionRange(begin = 1903, end = 2002)
    default RecipeFurnaceV1300 static$ofV1903_2002(Builder builder)
    {
        return this.static$ofV1903_2002(
            builder.getId(), builder.groupV1300, builder.cookingCategoryV1903,
            builder.getIngredientV1300(), builder.getResult(), builder.experience, builder.cookingTimeV1300
        );
    }
    @VersionRange(begin = 1903, end = 2002)
    @WrapConstructor
    RecipeFurnaceV1300 static$ofV1903_2002(
        Identifier id,
        String group,
        RecipeCookingCategoryV1903 category,
        IngredientVanilla ingredient,
        ItemStack result,
        float experience,
        int cookingTime);

    @SpecificImpl("static$of")
    @VersionRange(begin = 2002)
    default RecipeFurnaceV1300 static$ofV2002(Builder builder)
    {
        return this.static$ofV2002(
            builder.groupV1300, builder.cookingCategoryV1903,
            builder.getIngredientV1300(), builder.getResult(), builder.experience, builder.cookingTimeV1300
        );
    }
    @VersionRange(begin = 2002)
    @WrapConstructor
    RecipeFurnaceV1300 static$ofV2002(
        String group,
        RecipeCookingCategoryV1903 category,
        IngredientVanilla ingredient,
        ItemStack result,
        float experience,
        int cookingTime);
}
