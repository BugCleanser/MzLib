package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.RegistryKeyV1600;
import mz.mzlib.minecraft.registry.RegistryKeys;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2002)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.RecipeEntry"))
public interface RecipeEntryV2002 extends WrapperObject
{
    WrapperFactory<RecipeEntryV2002> FACTORY = WrapperFactory.of(RecipeEntryV2002.class);

    static RecipeEntryV2002 of(RecipeRegistration recipe)
    {
        return FACTORY.getStatic().static$of(recipe);
    }

    @VersionRange(end = 2102)
    static RecipeEntryV2002 newInstanceV_2102(Identifier key, Recipe value)
    {
        return FACTORY.getStatic().static$newInstanceV_2102(key, value);
    }
    @VersionRange(begin = 2102)
    static RecipeEntryV2002 newInstanceV2102(RegistryKeyV1600 key, Recipe value)
    {
        return FACTORY.getStatic().static$newInstanceV2102(key, value);
    }

    @VersionRange(end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "id"))
    Identifier getKeyV_2102();

    @VersionRange(begin = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "id"))
    RegistryKeyV1600 getKeyV2102();

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    Recipe getValue();


    RecipeEntryV2002 static$of(RecipeRegistration recipe);
    @SpecificImpl("static$of")
    @VersionRange(end = 2102)
    default RecipeEntryV2002 static$ofV_2102(RecipeRegistration recipe)
    {
        return newInstanceV_2102(recipe.getId(), recipe.getRecipe());
    }
    @SpecificImpl("static$of")
    @VersionRange(begin = 2102)
    default RecipeEntryV2002 static$ofV2102(RecipeRegistration recipe)
    {
        return newInstanceV2102(RegistryKeyV1600.of(RegistryKeys.RECIPE, recipe.getId()), recipe.getRecipe());
    }

    @VersionRange(end = 2102)
    @WrapConstructor
    RecipeEntryV2002 static$newInstanceV_2102(Identifier key, Recipe value);

    @VersionRange(begin = 2102)
    @WrapConstructor
    RecipeEntryV2002 static$newInstanceV2102(RegistryKeyV1600 key, Recipe value);
}
