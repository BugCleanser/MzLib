package mz.mzlib.minecraft.recipe.crafting;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtIo;
import mz.mzlib.minecraft.recipe.IngredientVanilla;
import mz.mzlib.minecraft.recipe.RecipeMojang;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.WrapperFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.recipe.ShapelessRecipeType", end = 1400),
    @VersionName(name = "net.minecraft.recipe.ShapelessRecipe", begin = 1400)
})
public interface RecipeCraftingShapeless extends RecipeMojang, RecipeCrafting
{
    WrapperFactory<RecipeCraftingShapeless> FACTORY = WrapperFactory.of(RecipeCraftingShapeless.class);

    @VersionRange(end = 1200)
    default List<IngredientVanilla> getIngredientsV_1200()
    {
        return new ListProxy<>(this.getIngredients0V_1200(), FunctionInvertible.wrapper(IngredientVanilla.FACTORY));
    }

    @Override
    default Identifier calcIdV_1200()
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try(DataOutputStream dataOutput = new DataOutputStream(stream))
        {
            for(IngredientVanilla ingredient : this.getIngredientsV_1200())
            {
                NbtIo.write(
                    ingredient.as(ItemStack.FACTORY).encode().getOrThrow(IllegalStateException::new).unwrap(),
                    dataOutput
                );
            }
        }
        catch(IOException e)
        {
            throw new IllegalStateException(e);
        }
        return Identifier.minecraft(UUID.nameUUIDFromBytes(stream.toByteArray()).toString());
    }


    @VersionRange(end = 1200)
    @WrapMinecraftFieldAccessor(@VersionName(name = "stacks"))
    List<Object> getIngredients0V_1200();
}
