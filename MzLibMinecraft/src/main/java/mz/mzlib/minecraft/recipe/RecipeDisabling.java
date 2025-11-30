package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.module.MzModule;
import mz.mzlib.module.Registrable;
import mz.mzlib.util.Option;

public class RecipeDisabling implements Registrable
{
    RecipeType type;
    Identifier id;

    public RecipeDisabling(RecipeType type, Identifier id)
    {
        this.type = type;
        this.id = id;
    }
    public RecipeDisabling(RecipeType type, Identifier id, Recipe recipe)
    {
        this(type, id);
        this.setRecipe(recipe);
    }

    Option<Recipe> recipe = Option.none();

    public Option<Recipe> getRecipe()
    {
        return this.recipe;
    }
    public void setRecipe(Recipe recipe)
    {
        this.recipe = Option.some(recipe);
    }

    @Override
    public void onRegister(MzModule module)
    {
        RegistrarRecipe.instance.disable(this);
    }
    @Override
    public void onUnregister(MzModule module)
    {
        RegistrarRecipe.instance.enable(this);
    }
}
