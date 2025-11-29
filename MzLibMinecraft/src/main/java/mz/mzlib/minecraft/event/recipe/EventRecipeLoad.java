package mz.mzlib.minecraft.event.recipe;

import mz.mzlib.event.Cancellable;
import mz.mzlib.event.Event;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.recipe.Recipe;

/**
 * @see mz.mzlib.minecraft.recipe.RegistrarRecipe
 */
public class EventRecipeLoad extends Event implements Cancellable
{
    Identifier id;
    Recipe recipe;

    public EventRecipeLoad(Identifier id, Recipe recipe)
    {
        this.id = id;
        this.recipe = recipe;
    }

    public Identifier getId()
    {
        return this.id;
    }
    public Recipe getRecipe()
    {
        return this.recipe;
    }

    @Override
    public void call()
    {
    }
}
