package mz.mzlib.minecraft.recipe.crafting;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.recipe.RecipeVanilla;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 1400)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.CraftingRecipe"))
public interface RecipeVanillaCraftingV1400 extends RecipeVanilla, RecipeCrafting
{
    WrapperFactory<RecipeVanillaCraftingV1400> FACTORY = WrapperFactory.of(RecipeVanillaCraftingV1400.class);
}
