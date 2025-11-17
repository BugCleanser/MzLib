package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegistrarRecipe implements IRegistrar<RecipeRegistration>
{
    public static RegistrarRecipe instance = new RegistrarRecipe();

    PreparedRecipes raw;
    Set<RecipeRegistration> recipes = new HashSet<>();

    public PreparedRecipes apply()
    {
        List<RecipeEntry> r = new ArrayList<>(this.raw.recipes());
        for(RecipeRegistration recipe : this.recipes)
        {
            r.add(RecipeEntry.of(recipe));
        }
        return PreparedRecipes.of(r);
    }
    public void flush()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManager();
        if(this.raw == null)
            this.raw = recipeManager.getPreparedRecipes();
        recipeManager.setPreparedRecipes(this.apply());
        recipeManager.initialize(MinecraftServer.instance.getSaveProperties().getEnabledFeatures());
    }

    @Override
    public Class<RecipeRegistration> getType()
    {
        return RecipeRegistration.class;
    }
    @Override
    public synchronized void register(MzModule module, RecipeRegistration object)
    {
        recipes.add(object);
        this.flush();
    }
    @Override
    public synchronized void unregister(MzModule module, RecipeRegistration object)
    {
        recipes.remove(object);
        this.flush();
    }
}
