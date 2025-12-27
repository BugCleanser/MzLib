package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.recipe.RecipeEntryV2002;
import mz.mzlib.minecraft.recipe.RecipeMojang;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.proxy.CollectionProxy;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;

import java.util.Collection;
import java.util.List;

@VersionRange(begin = 1300, end = 2102)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.class_4382", end = 1400),
    @VersionName(name = "net.minecraft.class_2788", begin = 1400) // SynchronizeRecipesS2CPacket
})
public interface PacketS2cRecipesV1300_2102 extends Packet
{
    WrapperFactory<PacketS2cRecipesV1300_2102> FACTORY = WrapperFactory.of(PacketS2cRecipesV1300_2102.class);

    default List<RecipeMojang> getRecipesV_2002()
    {
        return new ListProxy<>(this.getRecipes0(), FunctionInvertible.wrapper(RecipeMojang.FACTORY));
    }
    default List<RecipeEntryV2002> getRecipesV2002()
    {
        return new ListProxy<>(this.getRecipes0(), FunctionInvertible.wrapper(RecipeEntryV2002.FACTORY));
    }

    static PacketS2cRecipesV1300_2102 ofV_2002(Collection<RecipeMojang> recipes)
    {
        return FACTORY.getStatic().static$of0(CollectionProxy.of(recipes, FunctionInvertible.wrapper(RecipeMojang.FACTORY).inverse()));
    }
    static PacketS2cRecipesV1300_2102 ofV2002(Collection<RecipeEntryV2002> recipes)
    {
        return FACTORY.getStatic().static$of0(CollectionProxy.of(recipes, FunctionInvertible.wrapper(RecipeEntryV2002.FACTORY).inverse()));
    }


    @WrapMinecraftFieldAccessor({
        @VersionName(name = "field_21574", end = 1400),
        @VersionName(name = "recipes", begin = 1400)
    })
    List<Object> getRecipes0();
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "field_21574", end = 1400),
        @VersionName(name = "recipes", begin = 1400)
    })
    void setRecipes0(List<Object> value);

    @WrapConstructor
    PacketS2cRecipesV1300_2102 static$of0(Collection<Object> recipes);
}
