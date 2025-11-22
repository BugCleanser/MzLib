package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtIo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RecipeSmeltingV_1300 implements RecipeSmelting
{
    ItemStack ingredient;
    ItemStack result;
    float experience;

    public RecipeSmeltingV_1300(ItemStack ingredient, ItemStack result, float experience)
    {
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
    }

    public ItemStack getIngredient()
    {
        return this.ingredient;
    }
    public ItemStack getResult()
    {
        return this.result;
    }
    public float getExperience()
    {
        return this.experience;
    }

    @Override
    public RecipeType getType()
    {
        return RecipeType.SMELTING;
    }
    @Override
    public List<ItemStack> getIcons()
    {
        return Collections.singletonList(this.getResult());
    }

    public Identifier calcId()
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try(DataOutputStream dataOutput = new DataOutputStream(stream))
        {
            NbtIo.write(this.ingredient.encode().getOrThrow(IllegalStateException::new).unwrap(), dataOutput);
            dataOutput.writeFloat(this.experience);
        }
        catch(IOException e)
        {
            throw new IllegalStateException(e);
        }
        return Identifier.minecraft(UUID.nameUUIDFromBytes(stream.toByteArray()).toString());
    }

    public static class Builder extends RecipeSmelting.Builder
    {
        ItemStack ingredient;

        @Override
        public Builder ingredient(ItemStack value)
        {
            this.ingredient = value;
            return this;
        }

        public ItemStack getIngredient()
        {
            return Objects.requireNonNull(this.ingredient);
        }

        @Override
        public RecipeSmeltingV_1300 build()
        {
            return new RecipeSmeltingV_1300(this.getIngredient(), this.getResult(), this.experience);
        }
    }
}
