package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1200)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.Ingredient"))
public interface VanillaIngredientV1200 extends WrapperObject
{
    WrapperFactory<VanillaIngredientV1200> FACTORY = WrapperFactory.of(VanillaIngredientV1200.class);

    @WrapMinecraftMethod({
        @VersionName(name = "apply", end = 1300),
        @VersionName(name = "test", begin = 1300)
    })
    boolean test(ItemStack itemStack);
}
