package mz.mzlib.minecraft.recipe.smelting;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.recipe.RecipeMojang;
import mz.mzlib.minecraft.recipe.book.RecipeCookingCategoryV1903;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapInnerClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1400)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.AbstractCookingRecipe"))
public interface RecipeSmeltingV1400 extends RecipeMojang
{
    WrapperFactory<RecipeSmeltingV1400> FACTORY = WrapperFactory.of(RecipeSmeltingV1400.class);

    @VersionRange(begin = 2610)
    @WrapInnerClass(outer = RecipeSmeltingV1400.class, name = "CookingBookInfo")
    interface CookingBookInfoV2610 extends WrapperObject
    {
        WrapperFactory<CookingBookInfoV2610> FACTORY = WrapperFactory.of(CookingBookInfoV2610.class);

        static CookingBookInfoV2610 of(RecipeCookingCategoryV1903 category, String group)
        {
            return FACTORY.getStatic().static$of(category, group);
        }


        @WrapConstructor
        CookingBookInfoV2610 static$of(RecipeCookingCategoryV1903 category, String group);
    }
}
