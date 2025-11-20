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
    @VersionName(name = "net.minecraft.recipe.RecipeDispatcher", end = 1400),
    @VersionName(name = "net.minecraft.recipe.RecipeManager", begin = 1400, end = 2102),
    @VersionName(name = "net.minecraft.recipe.ServerRecipeManager", begin = 2102)
})
public interface RecipeManager extends WrapperObject
{
    WrapperFactory<RecipeManager> FACTORY = WrapperFactory.of(RecipeManager.class);

    @VersionRange(begin = 1300, end = 1400)
    @WrapMinecraftFieldAccessor(@VersionName(name = "field_17445"))
    Map<Object, Object> getRecipes0V1300_1400();
    @VersionRange(begin = 1300, end = 1400)
    @WrapMinecraftFieldAccessor(@VersionName(name = "field_17445"))
    void setRecipes0V1300_1400(Map<Object, Object> value);

    @VersionRange(begin = 1400, end = 2005)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipes"))
    Map<Object, Map<Object, Object>> getRecipes0V1400_2005();
    @VersionRange(begin = 1400, end = 2005)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipes"))
    void setRecipes0V1400_2005(Map<Object, Map<Object, Object>> value);

    @VersionRange(begin = 2005, end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipesByType"))
    Multimap<Object, Object> getTypeRecipes0V2005_2102();
    @VersionRange(begin = 2005, end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipesByType"))
    void setTypeRecipes0V2005_2102(Multimap<Object, Object> value);

    @VersionRange(begin = 1800, end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipesById"))
    Map<Object, Object> getIdRecipes0V1800_2102();
    @VersionRange(begin = 1800, end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "recipesById"))
    void setIdRecipes0V1800_2102(Map<Object, Object> value);

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
