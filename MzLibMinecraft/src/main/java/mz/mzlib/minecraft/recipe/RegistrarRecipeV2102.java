package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.nothing.*;

import java.util.ArrayList;
import java.util.List;

@VersionRange(begin = 2102)
public class RegistrarRecipeV2102 extends RegistrarRecipe
{
    public static RegistrarRecipeV2102 instance;

    PreparedRecipesV2102 raw;

    public PreparedRecipesV2102 apply()
    {
        List<RecipeEntryV2002> r = new ArrayList<>(this.raw.recipes());
        for(RecipeRegistration recipe : this.recipes)
        {
            r.add(RecipeEntryV2002.of(recipe));
        }
        return PreparedRecipesV2102.of(r);
    }

    @Override
    protected void setRaw()
    {
        this.raw = MinecraftServer.instance.getRecipeManager().getPreparedRecipesV2102();
    }

    @Override
    public void flush()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManager();
        if(this.raw == null)
            this.raw = recipeManager.getPreparedRecipesV2102();
        recipeManager.setPreparedRecipesV2102(this.apply());
        recipeManager.initializeV2102(MinecraftServer.instance.getSavePropertiesV1600().getEnabledFeaturesV1903());
    }
}
