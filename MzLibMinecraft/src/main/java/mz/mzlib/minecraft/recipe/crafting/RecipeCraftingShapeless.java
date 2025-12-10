package mz.mzlib.minecraft.recipe.crafting;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.recipe.RecipeMojang;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.recipe.ShapelessRecipeType", end = 1400),
    @VersionName(name = "net.minecraft.recipe.ShapelessRecipe", begin = 1400)
})
public interface RecipeCraftingShapeless extends RecipeMojang, RecipeCrafting
{
    WrapperFactory<RecipeCraftingShapeless> FACTORY = WrapperFactory.of(RecipeCraftingShapeless.class);
}
