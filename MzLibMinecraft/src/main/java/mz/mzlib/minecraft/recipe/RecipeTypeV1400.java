package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1400)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.RecipeType"))
public interface RecipeTypeV1400 extends WrapperObject
{
    WrapperFactory<RecipeTypeV1400> FACTORY = WrapperFactory.of(RecipeTypeV1400.class);
}
