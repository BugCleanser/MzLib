package mz.mzlib.minecraft.recipe.book;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1903)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.book.CraftingRecipeCategory"))
public interface RecipeCraftingCategoryV1903 extends WrapperObject
{
    WrapperFactory<RecipeCraftingCategoryV1903> FACTORY = WrapperFactory.of(RecipeCraftingCategoryV1903.class);

    RecipeCraftingCategoryV1903 BUILDING = FACTORY.getStatic().static$BUILDING();
    RecipeCraftingCategoryV1903 REDSTONE = FACTORY.getStatic().static$REDSTONE();
    RecipeCraftingCategoryV1903 EQUIPMENT = FACTORY.getStatic().static$EQUIPMENT();
    RecipeCraftingCategoryV1903 MISC = FACTORY.getStatic().static$MISC();

    @WrapMinecraftFieldAccessor(@VersionName(name = "BUILDING"))
    RecipeCraftingCategoryV1903 static$BUILDING();

    @WrapMinecraftFieldAccessor(@VersionName(name = "REDSTONE"))
    RecipeCraftingCategoryV1903 static$REDSTONE();

    @WrapMinecraftFieldAccessor(@VersionName(name = "EQUIPMENT"))
    RecipeCraftingCategoryV1903 static$EQUIPMENT();

    @WrapMinecraftFieldAccessor(@VersionName(name = "MISC"))
    RecipeCraftingCategoryV1903 static$MISC();
}
