package mz.mzlib.minecraft.recipe.crafting;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.recipe.RecipeMojang;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 1400)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.CraftingRecipe"))
public interface RecipeCraftingV1400 extends RecipeMojang, RecipeCrafting
{
    WrapperFactory<RecipeCraftingV1400> FACTORY = WrapperFactory.of(RecipeCraftingV1400.class);
}
