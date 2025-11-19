package mz.mzlib.minecraft.recipe;

import com.google.common.collect.Multimap;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.resource.FeatureSetV1903;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Map;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.recipe.RecipeManager", end = 2102),
    @VersionName(name = "net.minecraft.recipe.ServerRecipeManager", begin = 2102)
})
public interface RecipeManager extends WrapperObject
{
    WrapperFactory<RecipeManager> FACTORY = WrapperFactory.of(RecipeManager.class);

    @VersionRange(end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipesById"))
    Map<Object, Object> getIdRecipes0V_2102();
    @VersionRange(end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipesById"))
    void setIdRecipes0V_2102(Map<Object, Object> value);


    @VersionRange(end = 2005)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipes"))
    Map<Object, Map<Object, Object>> getRecipes0V_2005();
    @VersionRange(end = 2005)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipes"))
    void setRecipes0V_2005(Map<Object, Map<Object, Object>> value);

    @VersionRange(begin = 2005, end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipesByType"))
    Multimap<Object, Object> getTypeRecipes0V2005_2102();
    @VersionRange(begin = 2005, end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipesByType"))
    void setTypeRecipes0V2005_2102(Multimap<Object, Object> value);


    @VersionRange(begin = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "preparedRecipes"))
    PreparedRecipesV2102 getPreparedRecipesV2102();
    @VersionRange(begin = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "preparedRecipes"))
    void setPreparedRecipesV2102(PreparedRecipesV2102 value);

    @VersionRange(begin = 2102)
    @WrapMinecraftMethod(@VersionName(name = "initialize"))
    void initializeV2102(FeatureSetV1903 features);
}
