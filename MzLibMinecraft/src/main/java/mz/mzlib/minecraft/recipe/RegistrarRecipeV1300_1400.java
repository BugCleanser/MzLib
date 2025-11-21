package mz.mzlib.minecraft.recipe;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;

import java.util.Map;

@VersionRange(begin = 1300, end = 1400)
public class RegistrarRecipeV1300_1400 extends RegistrarRecipe
{
    public static RegistrarRecipeV1300_1400 instance;

    Map<Object, Object> rawRecipes0;

    public Map<Object, Object> apply()
    {
        Map<Object, Object> result = new Object2ObjectLinkedOpenHashMap<>(this.rawRecipes0); // adapt for Bukkit
        for(RecipeRegistration recipe : this.recipes)
        {
            result.put(recipe.getId().getWrapped(), recipe.getRecipeV1300().getWrapped());
        }
        return result;
    }

    @Override
    protected void setRaw()
    {
        this.rawRecipes0 = MinecraftServer.instance.getRecipeManagerV1300().getRecipes0V1300_1400();
    }

    @Override
    public void flush()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManagerV1300();
        if(this.rawRecipes0 == null)
            this.rawRecipes0 = recipeManager.getRecipes0V1300_1400();
        recipeManager.setRecipes0V1300_1400(this.apply());
    }
}
