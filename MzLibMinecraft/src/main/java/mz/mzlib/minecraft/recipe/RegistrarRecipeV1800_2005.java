package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@VersionRange(begin = 1800, end = 2005)
public class RegistrarRecipeV1800_2005 extends RegistrarRecipeV1400_2005
{
    public static RegistrarRecipeV1800_2005 instance;

    Map<Object, Object> rawIdRecipes0;

    public Map<Object, Object> applyIdRecipes0()
    {
        Map<Object, Object> result = new HashMap<>(this.rawIdRecipes0);
        for(RecipeRegistration recipe : this.recipes)
        {
            result.put(recipe.getId().getWrapped(), toData.apply(recipe).getWrapped());
        }
        return Collections.unmodifiableMap(result);
    }

    @Override
    protected void setRaw()
    {
        this.rawIdRecipes0 = MinecraftServer.instance.getRecipeManager().getIdRecipes0V1800_2102();
    }

    @Override
    public void flush()
    {
        super.flush();
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManager();
        if(this.rawIdRecipes0 == null)
            this.rawIdRecipes0 = recipeManager.getIdRecipes0V1800_2102();
        recipeManager.setIdRecipes0V1800_2102(this.applyIdRecipes0());
    }
}
