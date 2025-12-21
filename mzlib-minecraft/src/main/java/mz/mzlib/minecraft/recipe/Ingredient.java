package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.item.ItemStack;

import java.util.function.Predicate;

public interface Ingredient extends Predicate<ItemStack>
{
    /**
     * The number of items that this ingredient requires.
     */
    default int getCount()
    {
        return 1;
    }

    default Ingredient withCount(int count)
    {
        if(count == this.getCount())
            return this;
        return new Ingredient()
        {
            @Override
            public boolean test(ItemStack itemStack)
            {
                return Ingredient.this.test(itemStack);
            }

            @Override
            public int getCount()
            {
                return count;
            }
        };
    }
}
