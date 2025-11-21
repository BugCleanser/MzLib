package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.recipe.RawShapedRecipeV2003;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.recipe.book.RecipeCraftingCategoryV1903;
import mz.mzlib.minecraft.util.collection.DefaultedListV1100;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;

import java.util.List;
import java.util.stream.Collectors;

@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.recipe.ShapedRecipeType", end = 1400),
        @VersionName(name = "net.minecraft.recipe.ShapedRecipe", begin = 1400)
    }
)
public interface RecipeVanillaShaped extends RecipeVanilla, RecipeCrafting
{
    WrapperFactory<RecipeVanillaShaped> FACTORY = WrapperFactory.of(RecipeVanillaShaped.class);

    static RecipeVanillaShaped newInstance(Builder builder)
    {
        return FACTORY.getStatic().static$newInstance(builder);
    }

    static Builder builder()
    {
        return new Builder();
    }

    class Builder
    {
        Integer width;
        Integer height;
        List<Option<VanillaIngredient>> ingredients;
        ItemStack result;
        Identifier id;
        String groupV1200 = "";
        boolean showNotificationV1904 = true;
        RecipeCraftingCategoryV1903 categoryV1903 = CATEGORY_DEFAULT_V1903;
        static RecipeCraftingCategoryV1903 CATEGORY_DEFAULT_V1903 =
            MinecraftPlatform.instance.getVersion() < 1903 ? null : RecipeCraftingCategoryV1903.MISC;

        public RecipeVanillaShaped build()
        {
            if(this.width == null || this.height == null)
                throw new IllegalStateException("size is not set");
            if(this.ingredients == null)
                throw new IllegalStateException("ingredients are not set");
            if(this.result == null)
                throw new IllegalStateException("result is not set");
            return RecipeVanillaShaped.newInstance(this);
        }
        public RecipeRegistration buildRegistration()
        {
            return RecipeRegistration.of(this.getId(), this.build());
        }

        public Builder width(int value)
        {
            this.width = value;
            return this;
        }

        public Builder height(int value)
        {
            this.height = value;
            return this;
        }

        public Builder ingredients(List<Option<VanillaIngredient>> value)
        {
            this.ingredients = value;
            return this;
        }

        public Builder result(ItemStack value)
        {
            this.result = value;
            return this;
        }

        public Builder id(Identifier value)
        {
            this.id = value;
            return this;
        }

        public Builder groupV1200(String value)
        {
            this.groupV1200 = value;
            return this;
        }

        public Builder showNotificationV1904(boolean value)
        {
            this.showNotificationV1904 = value;
            return this;
        }

        public Builder categoryV1903(RecipeCraftingCategoryV1903 value)
        {
            this.categoryV1903 = value;
            return this;
        }

        public Identifier getId()
        {
            if(this.id == null)
                throw new IllegalStateException("id is not set");
            return this.id;
        }
        DefaultedListV1100<?> getIngredientsV1200_2003()
        {
            return DefaultedListV1100.fromWrapper(
                this.ingredients.stream().map(VanillaIngredient::fromOptionV_2102).collect(Collectors.toList()),
                VanillaIngredient.emptyV_2102()
            );
        }
    }


    RecipeVanillaShaped static$newInstance(Builder builder);

    @SpecificImpl("static$newInstance")
    @VersionRange(end = 1200)
    default RecipeVanillaShaped static$newInstanceV_1200(Builder builder)
    {
        ItemStack.Array array = ItemStack.Array.newInstance(builder.ingredients.size());
        for(int i = 0; i < builder.ingredients.size(); i++)
        {
            array.set(i, VanillaIngredient.fromOptionV_2102(builder.ingredients.get(i)).as(ItemStack.FACTORY));
        }
        return this.static$newInstanceV_1200(builder.width, builder.height, array, builder.result);
    }
    @VersionRange(end = 1200)
    @WrapConstructor
    RecipeVanillaShaped static$newInstanceV_1200(int width, int height, ItemStack.Array ingredients, ItemStack result);

    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 1200, end = 1300)
    default RecipeVanillaShaped static$newInstanceV1200_1300(Builder builder)
    {
        return this.static$newInstance0V1200_1300(
            builder.groupV1200, builder.width, builder.height, builder.getIngredientsV1200_2003(), builder.result);
    }
    @VersionRange(begin = 1200, end = 1300)
    @WrapConstructor
    RecipeVanillaShaped static$newInstance0V1200_1300(
        String group,
        int width,
        int height,
        DefaultedListV1100<?> ingredients,
        ItemStack result);

    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 1300, end = 1903)
    default RecipeVanillaShaped static$newInstanceV1300_1903(Builder builder)
    {
        return this.static$newInstance0V1300_1903(
            builder.getId(), builder.groupV1200, builder.width, builder.height,
            builder.getIngredientsV1200_2003(), builder.result
        );
    }
    @VersionRange(begin = 1300, end = 1903)
    @WrapConstructor
    RecipeVanillaShaped static$newInstance0V1300_1903(
        Identifier id,
        String group,
        int width,
        int height,
        DefaultedListV1100<?> ingredients,
        ItemStack result);

    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 1903, end = 1904)
    default RecipeVanillaShaped static$newInstanceV1903_1904(Builder builder)
    {
        return this.static$newInstance0V1903_1904(
            builder.getId(), builder.groupV1200, builder.categoryV1903, builder.width, builder.height,
            builder.getIngredientsV1200_2003(), builder.result
        );
    }
    @VersionRange(begin = 1903, end = 1904)
    @WrapConstructor
    RecipeVanillaShaped static$newInstance0V1903_1904(
        Identifier id,
        String group,
        RecipeCraftingCategoryV1903 category,
        int width,
        int height,
        DefaultedListV1100<?> ingredients,
        ItemStack result
    );

    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 1904, end = 2002)
    default RecipeVanillaShaped static$newInstanceV1904_2002(Builder builder)
    {
        return this.static$newInstance0V1904_2002(
            builder.getId(), builder.groupV1200, builder.categoryV1903, builder.width, builder.height,
            builder.getIngredientsV1200_2003(), builder.result, builder.showNotificationV1904
        );
    }
    @VersionRange(begin = 1904, end = 2002)
    @WrapConstructor
    RecipeVanillaShaped static$newInstance0V1904_2002(
        Identifier id,
        String group,
        RecipeCraftingCategoryV1903 category,
        int width,
        int height,
        DefaultedListV1100<?> ingredients,
        ItemStack result,
        boolean showNotification
    );

    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 2002, end = 2003)
    default RecipeVanillaShaped static$newInstanceV2002_2003(Builder builder)
    {
        return this.static$newInstance0V2002_2003(
            builder.groupV1200, builder.categoryV1903, builder.width, builder.height,
            builder.getIngredientsV1200_2003(), builder.result, builder.showNotificationV1904
        );
    }
    @VersionRange(begin = 2002, end = 2003)
    @WrapConstructor
    RecipeVanillaShaped static$newInstance0V2002_2003(
        String group,
        RecipeCraftingCategoryV1903 category,
        int width,
        int height,
        DefaultedListV1100<?> ingredients,
        ItemStack result,
        boolean showNotification);

    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 2003)
    default RecipeVanillaShaped static$newInstanceV2003(Builder builder)
    {
        return this.static$newInstanceV2003(
            builder.groupV1200, builder.categoryV1903,
            RawShapedRecipeV2003.newInstance(builder.width, builder.height, builder.ingredients, Option.none()),
            builder.result, builder.showNotificationV1904
        );
    }
    @VersionRange(begin = 2003)
    @WrapConstructor
    RecipeVanillaShaped static$newInstanceV2003(
        String group,
        RecipeCraftingCategoryV1903 category,
        RawShapedRecipeV2003 raw,
        ItemStack result,
        boolean showNotification);
}
