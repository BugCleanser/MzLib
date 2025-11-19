package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.RecipeType"))
public interface RecipeType extends WrapperObject
{
    WrapperFactory<RecipeType> FACTORY = WrapperFactory.of(RecipeType.class);
}
