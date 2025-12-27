package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.RegistryKeyV1600;
import mz.mzlib.minecraft.registry.RegistryKeysV1600;
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

    static RecipeEntryV2002 of(RecipeRegistration<? extends RecipeMojang> recipe)
    {
        return of(recipe.getId(), recipe.getRecipe());
    }
    static RecipeEntryV2002 of(Identifier key, RecipeMojang value)
    {
        return FACTORY.getStatic().static$of(key, value);
    }

    Identifier getId();
    @SpecificImpl("getId")
    @VersionRange(end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "id"))
    Identifier getKeyV_2102();
    @SpecificImpl("getId")
    @VersionRange(begin = 2102)
    default Identifier getIdV2102()
    {
        return this.getKeyV2102().getId();
    }

    @VersionRange(begin = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "id"))
    RegistryKeyV1600<RecipeMojang> getKeyV2102();

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    RecipeMojang getValue();


    RecipeEntryV2002 static$of(Identifier key, RecipeMojang value);
    @SpecificImpl("static$of")
    @VersionRange(end = 2102)
    @WrapConstructor
    RecipeEntryV2002 static$ofV_2102(Identifier key, RecipeMojang value);
    @SpecificImpl("static$of")
    @VersionRange(begin = 2102)
    default RecipeEntryV2002 static$ofV2102(Identifier key, RecipeMojang value)
    {
        return this.static$ofV2102(RegistryKeyV1600.of(RegistryKeysV1600.RECIPE, key), value);
    }
    @VersionRange(begin = 2102)
    @WrapConstructor
    RecipeEntryV2002 static$ofV2102(RegistryKeyV1600<RecipeMojang> key, RecipeMojang value);
}
