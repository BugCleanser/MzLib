package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Map;

@VersionRange(end = 1300)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.SmeltingRecipeRegistry"))
public interface SmeltingManagerV_1300 extends WrapperObject
{
    WrapperFactory<SmeltingManagerV_1300> FACTORY = WrapperFactory.of(SmeltingManagerV_1300.class);

    static SmeltingManagerV_1300 getInstance()
    {
        return FACTORY.getStatic().static$getInstance();
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "ORIGINAL_PRODUCT_MAP"))
    Map<Object, Object> getResults0();
    @WrapMinecraftFieldAccessor(@VersionName(name = "ORIGINAL_PRODUCT_MAP"))
    void setResults0(Map<Object, Object> value);

    /**
     * product to xp
     */
    @WrapMinecraftFieldAccessor(@VersionName(name = "PRODUCT_XP_MAP"))
    Map<Object, Float> getExperiences0();
    @WrapMinecraftFieldAccessor(@VersionName(name = "PRODUCT_XP_MAP"))
    void setExperiences0(Map<Object, Float> value);

    static SmeltingManagerV_1300 of()
    {
        return FACTORY.getStatic().static$of();
    }


    @WrapMinecraftFieldAccessor(@VersionName(name = "INSTANCE"))
    SmeltingManagerV_1300 static$getInstance();

    @WrapConstructor
    SmeltingManagerV_1300 static$of();
}
