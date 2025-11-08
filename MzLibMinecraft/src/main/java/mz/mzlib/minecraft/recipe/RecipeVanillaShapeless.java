package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.recipe.ShapelessRecipeType", end = 1400),
    @VersionName(name = "net.minecraft.recipe.ShapelessRecipe", begin = 1400)
})
public interface RecipeVanillaShapeless extends Recipe
{
    WrapperFactory<RecipeVanillaShapeless> FACTORY = WrapperFactory.of(RecipeVanillaShapeless.class);
}
