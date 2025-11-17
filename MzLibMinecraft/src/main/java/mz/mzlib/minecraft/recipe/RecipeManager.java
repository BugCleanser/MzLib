package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.incomprehensible.resource.FeatureSet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.ServerRecipeManager"))
public interface RecipeManager extends WrapperObject
{
    WrapperFactory<RecipeManager> FACTORY = WrapperFactory.of(RecipeManager.class);

    @WrapMinecraftFieldAccessor(@VersionName(name = "preparedRecipes"))
    PreparedRecipes getPreparedRecipes();
    @WrapMinecraftFieldAccessor(@VersionName(name = "preparedRecipes"))
    void setPreparedRecipes(PreparedRecipes value);

    @WrapMinecraftMethod(@VersionName(name = "initialize"))
    void initialize(FeatureSet features);
}
