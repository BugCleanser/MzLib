package mz.mzlib.minecraft.recipe.input;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2100)
@WrapMinecraftClass(@VersionName(name="net.minecraft.recipe.input.RecipeInput"))
public interface RecipeInputV2100 extends WrapperObject
{
    WrapperFactory<RecipeInputV2100> FACTORY = WrapperFactory.of(RecipeInputV2100.class);

    @WrapMinecraftMethod(@VersionName(name = "method_59983"))
    int size();

    @WrapMinecraftMethod(@VersionName(name = "method_59984"))
    ItemStack get(int index);

    @WrapMinecraftMethod(@VersionName(name = "method_59987"))
    boolean isEmpty();
}
