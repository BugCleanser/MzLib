package mz.mzlib.minecraft.recipe;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceManager;
import mz.mzlib.minecraft.incomprehensible.resource.SinglePreparationResourceReloader;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapMethodFromBridge;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@VersionRange(end = 2005)
public class RegistrarRecipeV_2005 extends RegistrarRecipe
{
    public static RegistrarRecipeV_2005 instance;

    Map<Object, Object> rawIdRecipes0;
    Map<Object, Map<Object, Object>> rawRecipes0;

    Function<RecipeRegistration, WrapperObject> toData = MinecraftPlatform.instance.getVersion() < 2002 ?
        RecipeRegistration::getRecipe : RecipeEntryV2002::of;

    public Map<Object, Object> applyIdRecipes0()
    {
        Map<Object, Object> result = new HashMap<>(this.rawIdRecipes0);
        for(RecipeRegistration recipe : this.recipes)
        {
            result.put(recipe.getId().getWrapped(), toData.apply(recipe).getWrapped());
        }
        return Collections.unmodifiableMap(result);
    }
    public Map<Object, Map<Object, Object>> applyRecipes0()
    {
        Map<Object, Map<Object, Object>> result = new HashMap<>(this.rawRecipes0);
        for(Map.Entry<Object, Map<Object, Object>> entry : result.entrySet())
        {
            entry.setValue(new Object2ObjectLinkedOpenHashMap<>(entry.getValue())); // adapt for Bukkit
        }
        for(RecipeRegistration recipe : this.recipes)
        {
            result.computeIfAbsent(recipe.getRecipe().getType().getWrapped(), k -> new HashMap<>())
                .put(recipe.getId().getWrapped(), toData.apply(recipe).getWrapped());
        }
        return Collections.unmodifiableMap(result);
    }

    @Override
    public void flush()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManager();
        if(this.rawIdRecipes0 == null)
            this.rawIdRecipes0 = recipeManager.getIdRecipes0V_2102();
        if(this.rawRecipes0 == null)
            this.rawRecipes0 = recipeManager.getRecipes0V_2005();
        recipeManager.setIdRecipes0V_2102(this.applyIdRecipes0());
        recipeManager.setRecipes0V_2005(this.applyRecipes0());
    }

    @VersionRange(end = 2005)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManager extends Nothing, RecipeManager, SinglePreparationResourceReloader<Object>
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
            RegistrarRecipeV_2005.instance.rawIdRecipes0 = this.getIdRecipes0V_2102();
            RegistrarRecipeV_2005.instance.rawRecipes0 = this.getRecipes0V_2005();
            this.setIdRecipes0V_2102(RegistrarRecipeV_2005.instance.applyIdRecipes0());
            this.setRecipes0V_2005(RegistrarRecipeV_2005.instance.applyRecipes0());
        }
    }
}
