package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceManager;
import mz.mzlib.minecraft.incomprehensible.resource.SinglePreparationResourceReloaderV1300;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapMethodFromBridge;
import mz.mzlib.util.wrapper.WrapSameClass;

import java.util.Map;

public class ModuleRecipe extends MzModule
{
    public static ModuleRecipe instance = new ModuleRecipe();

    @Override
    public void onLoad()
    {
        if(MinecraftPlatform.instance.getVersion() < 1800)
            this.register(new RegistrarRecipeV_2005());
        else if(MinecraftPlatform.instance.getVersion() < 2005)
            this.register(new RegistrarRecipeV1800_2005());
        else if(MinecraftPlatform.instance.getVersion() < 2102)
            this.register(new RegistrarRecipeV2005_2102());
        else
            this.register(new RegistrarRecipeV2102());

        if(MinecraftPlatform.instance.getVersion() < 1400)
        {
            // TODO
        }
        else if(MinecraftPlatform.instance.getVersion() < 2102)
            this.register(NothingRecipeManagerV1400_2102.class);
        else
            this.register(NothingRecipeManagerV2102.class);
    }

    @VersionRange(begin = 1400, end = 2102)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV1400_2102 extends Nothing, RecipeManager, SinglePreparationResourceReloaderV1300<Object>
    {
        @WrapMethodFromBridge(name = "applyV1400", params = { Object.class, ResourceManager.class, Profiler.class })
        void apply$impl(Map<?, ?> prepared, ResourceManager manager, Profiler profiler);

        @NothingInject(
            wrapperMethodName = "apply$impl",
            wrapperMethodParams = { Map.class, ResourceManager.class, Profiler.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void apply$impl$inject()
        {
            RegistrarRecipe.instance.onReloadEnd();
        }
    }
    @VersionRange(begin = 2102)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV2102 extends Nothing, RecipeManager, SinglePreparationResourceReloaderV1300<Object>
    {
        @WrapMethodFromBridge(name = "prepareV1400", params = { ResourceManager.class, Profiler.class })
        PreparedRecipesV2102 prepare$impl(ResourceManager manager, Profiler profiler);

        @NothingInject(
            wrapperMethodName = "prepare$impl", wrapperMethodParams = { ResourceManager.class, Profiler.class },
            locateMethod = "locateAllReturn", type = NothingInjectType.INSERT_BEFORE
        )
        default void prepare$impl$inject()
        {
            RegistrarRecipe.instance.onReloadEnd();
        }
    }
}
