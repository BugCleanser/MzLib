package mz.mzlib.minecraft.recipe.input;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 2100)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.input.CraftingRecipeInput"))
public interface RecipeInputCraftingV2100 extends RecipeInputV2100
{
    WrapperFactory<RecipeInputCraftingV2100> FACTORY = WrapperFactory.of(RecipeInputCraftingV2100.class);

    @WrapMinecraftMethod(@VersionName(name = "getWidth"))
    int getWidth();
    @WrapMinecraftMethod(@VersionName(name = "getHeight"))
    int getHeight();
}
