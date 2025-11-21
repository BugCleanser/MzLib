package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionRange;

@VersionRange(end = 1300)
public class RegistrarRecipeV_1300 extends RegistrarRecipe
{
    public static RegistrarRecipeV_1300 instance;

    @Override
    protected void setRaw()
    {
    }
    @Override
    public void flush()
    {
        SmeltingManagerV_1300 next = SmeltingManagerV_1300.of();
        SmeltingManagerV_1300.getInstance().setExperiences0(next.getExperiences0());
        SmeltingManagerV_1300.getInstance().setResults0(next.getResults0());
    }
    public void onReloadEnd(SmeltingManagerV_1300 smeltingManager)
    {
        for(RecipeRegistration r : this.recipes)
        {
            if(!r.getRecipe().getType().equals(RecipeType.SMELTING))
                continue;
            RecipeSmeltingV_1300 recipe = (RecipeSmeltingV_1300) r.getRecipe();
            smeltingManager.getResults0().put(recipe.getIngredient().getWrapped(), recipe.getResult().getWrapped());
            smeltingManager.getExperiences0().put(recipe.getResult().getWrapped(), recipe.getExperience());
        }
    }
}
