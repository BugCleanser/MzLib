package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionRange;

import java.util.*;

@VersionRange(end = 1200)
public class RegistrarRecipeV_1200 extends RegistrarRecipeV_1300
{
    public static RegistrarRecipeV_1200 instance;

    @Override
    protected void onReloadEnd()
    {
    }

    @Override
    protected void updateOriginal()
    {
        this.updateOriginal(RecipeManager.getInstanceV_1200());
    }
    protected void updateOriginal(RecipeManager recipeManager)
    {
        super.updateOriginal();
        Map<RecipeType, Map<Identifier, Recipe>> result = new HashMap<>(this.originalRecipes);
        HashMap<Identifier, Recipe> craftingRecipes = new HashMap<>();
        for(RecipeVanilla recipe : recipeManager.getRecipesV_1200())
        {
            recipe = recipe.autoCast();
            craftingRecipes.put(recipe.calcIdV_1200(), recipe);
        }
        result.put(RecipeType.CRAFTING, Collections.unmodifiableMap(craftingRecipes));
        this.originalRecipes = Collections.unmodifiableMap(result);
    }

    @Override
    public synchronized void flush()
    {
        this.updateOriginal(RecipeManager.ofV_1200()); // dirty
        super.flush();
        List<Object> recipes0 = new ArrayList<>();
        for(Recipe recipe : this.getEnabledRecipes().getOrDefault(RecipeType.CRAFTING, Collections.emptyMap()).values())
        {
            recipes0.add(((RecipeVanilla)recipe).getWrapped());
        }
        RecipeManager.getInstanceV_1200().setRecipes0V_1200(recipes0);
    }
}
