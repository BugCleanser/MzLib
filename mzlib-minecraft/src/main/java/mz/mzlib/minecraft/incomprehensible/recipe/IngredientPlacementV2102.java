package mz.mzlib.minecraft.incomprehensible.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;
import java.util.Optional;

@VersionRange(begin = 2102)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.IngredientPlacement"))
public interface IngredientPlacementV2102 extends WrapperObject
{
    WrapperFactory<IngredientPlacementV2102> FACTORY = WrapperFactory.of(IngredientPlacementV2102.class);

    static IngredientPlacementV2102 ofShaped0(List<Optional<Object>> ingredients)
    {
        return FACTORY.getStatic().static$ofShaped0(ingredients);
    }


    @WrapMinecraftMethod(@VersionName(name = "forMultipleSlots"))
    IngredientPlacementV2102 static$ofShaped0(List<Optional<Object>> ingredients);
}

