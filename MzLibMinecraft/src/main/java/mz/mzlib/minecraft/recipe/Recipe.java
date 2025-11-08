package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.inventory.InventoryCrafting;
import mz.mzlib.minecraft.world.AbstractWorld;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.recipe.RecipeType", end = 1400),
    @VersionName(name = "net.minecraft.recipe.Recipe", begin = 1400)
})
public interface Recipe extends WrapperObject
{
    WrapperFactory<Recipe> FACTORY = WrapperFactory.of(Recipe.class);

    @VersionRange(end = 1300)
    @WrapMinecraftMethod(@VersionName(name = "matches"))
    boolean matchesV_1300(InventoryCrafting inventory, AbstractWorld world);
}
