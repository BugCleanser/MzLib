package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;

import java.util.*;

@VersionRange(begin = 2102)
public class RegistrarRecipeV2102 extends RegistrarRecipe
{
    public static RegistrarRecipeV2102 instance;

    PreparedRecipesV2102 raw;

    @Override
    protected void updateOriginal()
    {
        this.raw = MinecraftServer.instance.getRecipeManagerV1300().getPreparedRecipesV2102();
        Map<RecipeType, Map<Identifier, Recipe>> result = new HashMap<>();
        for(RecipeEntryV2002 recipe : this.raw.recipes())
        {
            RecipeVanilla r = recipe.getValue().autoCast();
            result.computeIfAbsent(r.getType(), k -> new HashMap<>())
                .put(recipe.getId(), r);
        }
        for(Map.Entry<RecipeType, Map<Identifier, Recipe>> entry : result.entrySet())
        {
            entry.setValue(Collections.unmodifiableMap(result.get(entry.getKey())));
        }
        this.originalRecipes = Collections.unmodifiableMap(result);
    }

    @Override
    public synchronized void flush()
    {
        super.flush();
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManagerV1300();
        List<RecipeEntryV2002> result = new ArrayList<>(this.raw.recipes());
        for(RecipeRegistration recipe : this.recipeRegistrations)
        {
            result.add(RecipeEntryV2002.of(recipe));
        }
        recipeManager.setPreparedRecipesV2102(PreparedRecipesV2102.of(result));
        recipeManager.initializeV2102(MinecraftServer.instance.getSavePropertiesV1600().getEnabledFeaturesV1903());
    }
}
