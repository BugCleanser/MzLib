package mz.mzlib.minecraft.recipe.book;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1903)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.book.CookingRecipeCategory"))
public interface RecipeCookingCategoryV1903 extends WrapperObject
{
    WrapperFactory<RecipeCookingCategoryV1903> FACTORY = WrapperFactory.of(RecipeCookingCategoryV1903.class);

    RecipeCookingCategoryV1903 FOOD = FACTORY.getStatic().static$FOOD();
    RecipeCookingCategoryV1903 BLOCKS = FACTORY.getStatic().static$BLOCKS();
    RecipeCookingCategoryV1903 MISC = FACTORY.getStatic().static$MISC();

    @WrapMinecraftFieldAccessor(@VersionName(name = "field_40242"))
    RecipeCookingCategoryV1903 static$FOOD();
    @WrapMinecraftFieldAccessor(@VersionName(name = "field_40243"))
    RecipeCookingCategoryV1903 static$BLOCKS();
    @WrapMinecraftFieldAccessor(@VersionName(name = "field_40244"))
    RecipeCookingCategoryV1903 static$MISC();
}
