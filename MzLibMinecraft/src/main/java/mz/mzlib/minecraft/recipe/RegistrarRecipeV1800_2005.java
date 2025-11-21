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

    @Override
    protected void updateOriginal()
    {
        super.updateOriginal();
        this.rawIdRecipes0 = MinecraftServer.instance.getRecipeManagerV1300().getIdRecipes0V1800_2102();
    }

    @Override
    public synchronized void flush()
    {
        super.flush();
        Map<Object, Object> result = new HashMap<>(this.rawIdRecipes0);
        for(RecipeRegistration recipe : this.recipeRegistrations)
        {
            result.put(recipe.getId().getWrapped(), toData.apply(recipe).getWrapped());
        }
        MinecraftServer.instance.getRecipeManagerV1300().setIdRecipes0V1800_2102(Collections.unmodifiableMap(result));
    }
}
