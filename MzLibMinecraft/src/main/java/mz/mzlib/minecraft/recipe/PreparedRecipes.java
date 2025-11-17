package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.proxy.CollectionProxy;
import mz.mzlib.util.proxy.IteratorProxy;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Collection;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.PreparedRecipes"))
public interface PreparedRecipes extends WrapperObject
{
    WrapperFactory<PreparedRecipes> FACTORY = WrapperFactory.of(PreparedRecipes.class);

    static PreparedRecipes of(Iterable<RecipeEntry> recipes)
    {
        return FACTORY.getStatic().static$of0(IteratorProxy.iterable(recipes, RecipeEntry::getWrapped));
    }

    default Collection<RecipeEntry> recipes()
    {
        return new CollectionProxy<>(this.recipes0(), FunctionInvertible.wrapper(RecipeEntry.FACTORY));
    }



    @WrapMinecraftMethod(@VersionName(name = "of"))
    PreparedRecipes static$of0(Iterable<Object> recipes0);

    @WrapMinecraftMethod(@VersionName(name = "recipes"))
    Collection<Object> recipes0();
}
