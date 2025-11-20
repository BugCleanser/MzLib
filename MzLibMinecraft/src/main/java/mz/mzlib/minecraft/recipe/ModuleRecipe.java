package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceManagerV1300;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceReloaderV1300;
import mz.mzlib.minecraft.incomprehensible.resource.SinglePreparationResourceReloaderV1400;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapMethodFromBridge;
import mz.mzlib.util.wrapper.WrapSameClass;

public class ModuleRecipe extends MzModule
{
    public static ModuleRecipe instance = new ModuleRecipe();

    @Override
    public void onLoad()
    {
        final int version = MinecraftPlatform.instance.getVersion();
        if(version < 1200)
            this.register(new RegistrarRecipeV_1200());
        else if(version < 1300)
            this.register(new RegistrarRecipeV1200_1300());
        else if(version < 1400)
            this.register(new RegistrarRecipeV1300_1400());
        else if(version < 1800)
            this.register(new RegistrarRecipeV1400_2005());
        else if(version < 2005)
            this.register(new RegistrarRecipeV1800_2005());
        else if(version < 2102)
            this.register(new RegistrarRecipeV2005_2102());
        else
            this.register(new RegistrarRecipeV2102());

        if(version < 1200)
            this.register(NothingRecipeManagerV_1200.class);
        else if(version < 1300)
            this.register(NothingRecipeManagerV1200_1300.class);
        else if(version < 1400)
            this.register(NothingRecipeManagerV1300_1400.class);
        else
            this.register(NothingRecipeManagerV1400.class);
    }

    @VersionRange(end = 1200)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV_1200 extends Nothing, RecipeManager
    {
        @NothingInject(
            wrapperMethodName = "<init>",
            wrapperMethodParams = {},
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void static$ofV_1200$end()
        {
            RegistrarRecipeV_1200.instance.onReloadEnd(this);
        }
    }
    @VersionRange(begin = 1200, end = 1300)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV1200_1300 extends Nothing, RecipeManager
    {
        @NothingInject(
            wrapperMethodName = "static$setupV1200_1300",
            wrapperMethodParams = {},
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        static void setupV1200_1300$end()
        {
            RegistrarRecipe.instance.onReloadEnd();
        }
    }
    @VersionRange(begin = 1300, end = 1400)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV1300_1400 extends Nothing, RecipeManager, ResourceReloaderV1300
    {
        @NothingInject(
            wrapperMethodName = "reloadV_1400",
            wrapperMethodParams = { ResourceManagerV1300.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void reload$end()
        {
            RegistrarRecipe.instance.onReloadEnd();
        }
    }
    @VersionRange(begin = 1400)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV1400 extends Nothing, RecipeManager, SinglePreparationResourceReloaderV1400<Object>
    {
        @WrapMethodFromBridge(name = "applyV1400", params = { Object.class, ResourceManagerV1300.class, Profiler.class })
        void apply$impl(Object prepared, ResourceManagerV1300 manager, Profiler profiler);

        @NothingInject(
            wrapperMethodName = "apply$impl",
            wrapperMethodParams = { Object.class, ResourceManagerV1300.class, Profiler.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void apply$impl$end()
        {
            RegistrarRecipe.instance.onReloadEnd();
        }
    }
}
