package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Instance;
import mz.mzlib.util.Option;

import java.util.*;

/**
 * @see ModuleRecipe
 */
public abstract class RegistrarRecipe implements IRegistrar<RecipeRegistration>, Instance
{
    public static RegistrarRecipe instance;

    Set<RecipeRegistration> recipeRegistrations = new HashSet<>();
    Map<RecipeType, Map<Identifier, Recipe>> originalRecipes;
    Map<RecipeType, Map<Identifier, Recipe>> enabledRecipes = Collections.emptyMap();

    public Map<RecipeType, Map<Identifier, Recipe>> getEnabledRecipes()
    {
        return this.enabledRecipes;
    }

    Map<RecipeType, Map<Identifier, RecipeDisabling>> disabledRecipes = new HashMap<>();
    public void disable(RecipeDisabling recipe)
    {
        this.disabledRecipes.computeIfAbsent(recipe.type, k -> new HashMap<>()).put(recipe.id, recipe);
        this.markDirty();
    }
    public void enable(RecipeDisabling recipe)
    {
        this.disabledRecipes.get(recipe.type).remove(recipe.id);
        this.markDirty();
    }

    public Map<RecipeType, Map<Identifier, Recipe>> getOriginalRecipes()
    {
        if(this.originalRecipes == null)
            this.updateOriginal();
        return this.originalRecipes;
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
        Map<RecipeType, Map<Identifier, Recipe>> enabledRecipes = new HashMap<>(this.getOriginalRecipes());
        for(Map.Entry<RecipeType, Map<Identifier, Recipe>> entry : enabledRecipes.entrySet())
        {
            entry.setValue(new HashMap<>(entry.getValue()));
        }
        for(RecipeRegistration r : this.recipeRegistrations)
        {
            enabledRecipes.computeIfAbsent(r.getRecipe().getType(), k -> new HashMap<>())
                .put(r.getId(), r.getRecipe());
        }
        for(Map.Entry<RecipeType, Map<Identifier, Recipe>> entry : enabledRecipes.entrySet())
        {
            for(Iterator<Map.Entry<Identifier, Recipe>> i = entry.getValue().entrySet().iterator(); i.hasNext(); )
            {
                Map.Entry<Identifier, Recipe> e = i.next();
                for(RecipeDisabling disabling : Option.fromNullable(
                    this.disabledRecipes.getOrDefault(entry.getKey(), Collections.emptyMap()).get(e.getKey())))
                {
                    disabling.setRecipe(e.getValue());
                    i.remove();
                }
            }
        }
        for(Map.Entry<RecipeType, Map<Identifier, Recipe>> e : enabledRecipes.entrySet())
        {
            e.setValue(Collections.unmodifiableMap(e.getValue()));
        }
        this.enabledRecipes = Collections.unmodifiableMap(enabledRecipes);
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
