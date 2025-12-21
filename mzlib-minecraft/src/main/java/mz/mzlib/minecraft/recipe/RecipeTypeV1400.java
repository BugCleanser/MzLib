package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1400)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.RecipeType"))
public interface RecipeTypeV1400 extends WrapperObject, RecipeType
{
    WrapperFactory<RecipeTypeV1400> FACTORY = WrapperFactory.of(RecipeTypeV1400.class);

    RecipeTypeV1400 CRAFTING = FACTORY.getStatic().static$CRAFTING();
    RecipeTypeV1400 FURNACE = FACTORY.getStatic().static$SMELTING();

    @WrapMinecraftFieldAccessor(@VersionName(name = "field_17545"))
    RecipeTypeV1400 static$CRAFTING();
    @WrapMinecraftFieldAccessor(@VersionName(name = "field_17546"))
    RecipeTypeV1400 static$SMELTING();
}
