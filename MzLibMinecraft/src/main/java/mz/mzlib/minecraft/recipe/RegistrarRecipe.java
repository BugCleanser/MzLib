package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Instance;

import java.util.*;

@VersionRange(begin = 1400)
public abstract class RegistrarRecipe implements IRegistrar<RecipeRegistration>, Instance
{
    public static RegistrarRecipe instance;

    Set<RecipeRegistration> recipeRegistrations = new HashSet<>();
    Map<RecipeType, Map<Identifier, Recipe>> originalRecipes;
    Map<RecipeType, Map<Identifier, Recipe>> registeredRecipes = Collections.emptyMap();

    public Map<RecipeType, Map<Identifier, Recipe>> getOriginalRecipes()
    {
        if(this.originalRecipes == null)
            this.updateOriginal();
        return this.originalRecipes;
    }
    public Map<RecipeType, Map<Identifier, Recipe>> getRegisteredRecipes()
    {
        return this.registeredRecipes;
    }

    @Override
    public Class<RecipeRegistration> getType()
    {
        return RecipeRegistration.class;
    }

    boolean isDirty = false;

    public synchronized void markDirty()
    {
        if(this.isDirty)
            return;
        this.isDirty = true;
        MinecraftServer.instance.schedule(this::flush);
    }

    public synchronized void flush()
    {
        this.isDirty = false;
        Map<RecipeType, Map<Identifier, Recipe>> ignored = getOriginalRecipes();// update
        Map<RecipeType, Map<Identifier, Recipe>> result = new HashMap<>();
        for(RecipeRegistration r : this.recipeRegistrations)
        {
            result.computeIfAbsent(r.getRecipe().getType(), k -> new HashMap<>())
                .put(r.getId(), r.getRecipe());
        }
        for(Map.Entry<RecipeType, Map<Identifier, Recipe>> e : result.entrySet())
        {
            e.setValue(Collections.unmodifiableMap(e.getValue()));
        }
        this.registeredRecipes = Collections.unmodifiableMap(result);
    }

    protected abstract void updateOriginal();

    protected void onReloadEnd()
    {
        this.updateOriginal();
        this.flush();
    }

    @Override
    public synchronized void register(MzModule module, RecipeRegistration object)
    {
        this.recipeRegistrations.add(object);
        this.markDirty();
    }
    @Override
    public synchronized void unregister(MzModule module, RecipeRegistration object)
    {
        this.recipeRegistrations.remove(object);
        this.markDirty();
    }
}
