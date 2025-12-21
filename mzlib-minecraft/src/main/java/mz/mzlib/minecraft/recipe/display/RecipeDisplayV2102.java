package mz.mzlib.minecraft.recipe.display;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2102)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.display.RecipeDisplay"))
public interface RecipeDisplayV2102 extends WrapperObject
{
    WrapperFactory<RecipeDisplayV2102> FACTORY = WrapperFactory.of(RecipeDisplayV2102.class);

    @WrapMinecraftMethod(@VersionName(name = "comp_3258"))
    RecipeDisplaySlotV2102 getResult();
}
