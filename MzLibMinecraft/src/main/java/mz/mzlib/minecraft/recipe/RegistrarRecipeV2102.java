package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceManager;
import mz.mzlib.minecraft.incomprehensible.resource.SinglePreparationResourceReloaderV1605;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapMethodFromBridge;
import mz.mzlib.util.wrapper.WrapSameClass;

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
    public void flush()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManager();
        if(this.raw == null)
            this.raw = recipeManager.getPreparedRecipesV2102();
        recipeManager.setPreparedRecipesV2102(this.apply());
        recipeManager.initializeV2102(MinecraftServer.instance.getSavePropertiesV1600().getEnabledFeaturesV1903());
    }

    @VersionRange(begin = 2102)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManager extends Nothing, RecipeManager, SinglePreparationResourceReloaderV1605<Object>
    {
        @WrapMethodFromBridge(name = "prepare", params = { ResourceManager.class, Profiler.class })
        PreparedRecipesV2102 prepare$impl(ResourceManager manager, Profiler profiler);

        @NothingInject(
            wrapperMethodName = "prepare$impl", wrapperMethodParams = { ResourceManager.class, Profiler.class },
            locateMethod = "locateAllReturn", type = NothingInjectType.INSERT_BEFORE
        )
        default void prepare$impl$inject()
        {
            RegistrarRecipeV2102.instance.raw = this.getPreparedRecipesV2102();
            this.setPreparedRecipesV2102(RegistrarRecipeV2102.instance.apply());
        }
    }
}
