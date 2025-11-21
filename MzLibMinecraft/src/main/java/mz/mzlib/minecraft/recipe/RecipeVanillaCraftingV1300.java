package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 1300)
@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.class_3571", end = 1400),
        @VersionName(name = "net.minecraft.recipe.CraftingRecipe", begin = 1400)
    }
)
public interface RecipeVanillaCraftingV1300 extends RecipeVanilla, RecipeCrafting
{
    WrapperFactory<RecipeVanillaCraftingV1300> FACTORY = WrapperFactory.of(RecipeVanillaCraftingV1300.class);
}
