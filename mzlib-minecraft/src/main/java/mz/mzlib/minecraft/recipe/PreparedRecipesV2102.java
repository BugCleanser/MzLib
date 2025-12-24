package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.proxy.CollectionProxy;
import mz.mzlib.util.proxy.IteratorProxy;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Collection;

@VersionRange(begin = 2102)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.PreparedRecipes"))
public interface PreparedRecipesV2102 extends WrapperObject
{
    WrapperFactory<PreparedRecipesV2102> FACTORY = WrapperFactory.of(PreparedRecipesV2102.class);

    static PreparedRecipesV2102 of(Iterable<RecipeEntryV2002> recipes)
    {
        return FACTORY.getStatic().static$of0(IteratorProxy.iterable(recipes, RecipeEntryV2002::getWrapped));
    }

    default Collection<RecipeEntryV2002> recipes()
    {
        return CollectionProxy.of(this.recipes0(), FunctionInvertible.wrapper(RecipeEntryV2002.FACTORY));
    }


    @WrapMinecraftMethod(@VersionName(name = "of"))
    PreparedRecipesV2102 static$of0(Iterable<Object> recipes0);

    @WrapMinecraftMethod(@VersionName(name = "recipes"))
    Collection<Object> recipes0();
}
