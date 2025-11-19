package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceManager;
import mz.mzlib.minecraft.incomprehensible.resource.SinglePreparationResourceReloaderV1605;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapMethodFromBridge;
import mz.mzlib.util.wrapper.WrapSameClass;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@VersionRange(begin = 1800, end = 2005)
public class RegistrarRecipeV1800_2005 extends RegistrarRecipeV_2005
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
    public void flush()
    {
        super.flush();
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManager();
        if(this.rawIdRecipes0 == null)
            this.rawIdRecipes0 = recipeManager.getIdRecipes0V1800_2102();
        recipeManager.setIdRecipes0V1800_2102(this.applyIdRecipes0());
    }

    @VersionRange(begin = 1800, end = 2005)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManager extends Nothing, RecipeManager, SinglePreparationResourceReloaderV1605<Object>
    {
        @WrapMethodFromBridge(name = "apply", params = { Object.class, ResourceManager.class, Profiler.class })
        void apply$impl(Map<?, ?> prepared, ResourceManager manager, Profiler profiler);

        @NothingInject(
            wrapperMethodName = "apply$impl",
            wrapperMethodParams = { Map.class, ResourceManager.class, Profiler.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void apply$impl$inject()
        {
            RegistrarRecipeV1800_2005.instance.rawIdRecipes0 = this.getIdRecipes0V1800_2102();
            this.setIdRecipes0V1800_2102(RegistrarRecipeV1800_2005.instance.applyIdRecipes0());
        }
    }
}
