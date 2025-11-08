package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.recipe.ShapedRecipeType", end = 1400),
    @VersionName(name = "net.minecraft.recipe.ShapedRecipe", begin = 1400)
})
public interface RecipeVanillaShaped extends Recipe
{
    WrapperFactory<RecipeVanillaShaped> FACTORY = WrapperFactory.of(RecipeVanillaShaped.class);
}
