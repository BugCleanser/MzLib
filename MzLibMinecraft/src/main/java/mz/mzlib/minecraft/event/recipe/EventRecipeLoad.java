package mz.mzlib.minecraft.event.recipe;

import mz.mzlib.event.Cancellable;
import mz.mzlib.event.Event;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.recipe.Recipe;
import mz.mzlib.minecraft.recipe.RegistrarRecipe;

// TODO
/**
 * @see RegistrarRecipe
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

    @Override
    public void call()
    {
    }
}
