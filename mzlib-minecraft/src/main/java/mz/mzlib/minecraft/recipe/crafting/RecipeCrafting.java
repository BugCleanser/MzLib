package mz.mzlib.minecraft.recipe.crafting;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.recipe.RecipeMojang;
import mz.mzlib.minecraft.recipe.book.RecipeCraftingCategoryV1903;
import mz.mzlib.minecraft.recipe.input.RecipeInputCraftingV2100;
import mz.mzlib.minecraft.util.collection.DefaultedListV1100;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.recipe.RecipeType", end = 1400),
    @VersionName(name = "net.minecraft.recipe.CraftingRecipe", begin = 1400)
})
public interface RecipeCrafting extends RecipeMojang
{
    WrapperFactory<RecipeCrafting> FACTORY = WrapperFactory.of(RecipeCrafting.class);

    @VersionRange(begin = 1903)
    @WrapMinecraftMethod(@VersionName(name = "getCategory"))
    RecipeCraftingCategoryV1903 getCategoryV1903();

    @VersionRange(begin = 2102)
    @WrapMinecraftMethod(@VersionName(name = "method_17704"))
    DefaultedListV1100<?> getRemainders0V2102(RecipeInputCraftingV2100 input);
}
