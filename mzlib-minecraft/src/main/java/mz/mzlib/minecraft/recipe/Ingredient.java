package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Collections;
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

    default Collection<ItemStack> getExamples()
    {
        return Collections.emptyList();
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
