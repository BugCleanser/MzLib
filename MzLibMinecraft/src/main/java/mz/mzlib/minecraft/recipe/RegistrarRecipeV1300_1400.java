package mz.mzlib.minecraft.recipe;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@VersionRange(begin = 1300, end = 1400)
public class RegistrarRecipeV1300_1400 extends RegistrarRecipe
{
    public static RegistrarRecipeV1300_1400 instance;

    Map<Object, Object> rawRecipes0;

    @Override
    protected void updateOriginal()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManagerV1300();
        this.rawRecipes0 = recipeManager.getRecipes0V1300_1400();
        Map<RecipeType, Map<Identifier, Recipe>> result = new HashMap<>();
        for(Map.Entry<Identifier, RecipeVanilla> e : recipeManager.getRecipesV1300_1400().entrySet())
        {
            RecipeVanilla recipe = e.getValue().autoCast();
            result.computeIfAbsent(recipe.getType(), k -> new HashMap<>())
                .put(e.getKey(), recipe);
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
        Map<Object, Object> result = new Object2ObjectLinkedOpenHashMap<>(this.rawRecipes0); // adapt for Bukkit
        for(RecipeRegistration recipe : this.recipeRegistrations)
        {
            result.put(recipe.getId().getWrapped(), recipe.getRecipeV1300().getWrapped());
        }
        MinecraftServer.instance.getRecipeManagerV1300().setRecipes0V1300_1400(result);
    }
}
