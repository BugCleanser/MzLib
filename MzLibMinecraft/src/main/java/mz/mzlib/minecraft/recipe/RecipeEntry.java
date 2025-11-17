package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.RegistryKeyV1600;
import mz.mzlib.minecraft.registry.RegistryKeys;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.RecipeEntry"))
public interface RecipeEntry extends WrapperObject
{
    WrapperFactory<RecipeEntry> FACTORY = WrapperFactory.of(RecipeEntry.class);

    static RecipeEntry of(RecipeRegistration recipe)
    {
        return newInstance(RegistryKeyV1600.of(RegistryKeys.RECIPE, recipe.getId()), recipe.getRecipe());
    }

    static RecipeEntry newInstance(RegistryKeyV1600 key, Recipe value)
    {
        return FACTORY.getStatic().static$newInstance(key, value);
    }
    @WrapConstructor
    RecipeEntry static$newInstance(RegistryKeyV1600 key, Recipe value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "id"))
    RegistryKeyV1600 getKey();

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    Recipe getValue();
}
