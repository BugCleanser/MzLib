package mz.mzlib.minecraft.recipe.crafting;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventoryCrafting;
import mz.mzlib.minecraft.inventory.InventoryRecipeInputV2000;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.recipe.RecipeMojangAbstract;
import mz.mzlib.minecraft.recipe.book.RecipeCraftingCategoryV1903;
import mz.mzlib.minecraft.recipe.input.RecipeInputCraftingV2100;
import mz.mzlib.minecraft.recipe.input.RecipeInputV2100;
import mz.mzlib.minecraft.util.collection.DefaultedListV1100;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;

@Compound
public interface RecipeCraftingAbstract extends RecipeMojangAbstract<RecipeCraftingAbstract.Input>, RecipeCrafting
{
    WrapperFactory<RecipeCraftingAbstract> FACTORY = WrapperFactory.of(RecipeCraftingAbstract.class);

    @Override
    RecipeCrafting getDisplay();

    @Override
    @VersionRange(begin = 1903)
    RecipeCraftingCategoryV1903 getCategoryV1903();

    interface Input extends RecipeMojangAbstract.Input
    {
        int getWidth();
        int getHeight();
    }

    @Override
    @VersionRange(end = 2100)
    Input inputV_2100(Inventory inventory);
    @SpecificImpl("inputV_2100")
    @VersionRange(end = 2000)
    default Input inputV_2000(Inventory inventory)
    {
        return new InputAdapted(new InputV_2000(inventory.as(InventoryCrafting.FACTORY)));
    }
    @SpecificImpl("inputV_2100")
    @VersionRange(begin = 2000, end = 2100)
    default Input inputV2000_2100(Inventory inventory)
    {
        return new InputAdapted(new InputV2000_2100(inventory.as(InventoryRecipeInputV2000.FACTORY)));
    }
    @Override
    @VersionRange(begin = 2100)
    default Input inputV2100(RecipeInputV2100 input)
    {
        return new InputV2100(input.as(RecipeInputCraftingV2100.FACTORY));
    }
    class InputV_2000 extends RecipeMojangAbstract.InputV_2100<InventoryCrafting> implements Input
    {
        public InputV_2000(InventoryCrafting delegate)
        {
            super(delegate);
        }
        @Override
        public int getWidth()
        {
            return this.getDelegate().getWidthV_2000();
        }
        @Override
        public int getHeight()
        {
            return this.getDelegate().getHeightV_2000();
        }
    }
    class InputV2000_2100 extends RecipeMojangAbstract.InputV_2100<InventoryRecipeInputV2000> implements Input
    {
        public InputV2000_2100(InventoryRecipeInputV2000 delegate)
        {
            super(delegate);
        }
        @Override
        public int getWidth()
        {
            return this.getDelegate().getWidth();
        }
        @Override
        public int getHeight()
        {
            return this.getDelegate().getHeight();
        }
    }
    class InputAdapted implements Input
    {
        Input delegate;
        int xBegin, yBegin;
        int width, height;
        public InputAdapted(Input delegate)
        {
            this.delegate = delegate;
            this.xBegin = 0;
            this.yBegin = 0;
            this.width = delegate.getWidth();
            this.height = delegate.getHeight();
            while(this.width > 0)
            {
                boolean flag = false;
                for(int i = 0; i < this.height; i++)
                {
                    if(!this.get(i * this.getWidth()).isEmpty())
                        flag = true;
                }
                if(flag)
                    break;
                this.xBegin++;
                this.width--;
            }
            while(this.height > 0)
            {
                boolean flag = false;
                for(int i = 0; i < this.width; i++)
                {
                    if(!this.get(i).isEmpty())
                        flag = true;
                }
                if(flag)
                    break;
                this.yBegin++;
                this.height--;
            }
            while(this.width > 0)
            {
                boolean flag = false;
                for(int i = 0; i < this.height; i++)
                {
                    if(!this.get((this.width - 1) + i * this.getWidth()).isEmpty())
                        flag = true;
                }
                if(flag)
                    break;
                this.width--;
            }
            while(this.height > 0)
            {
                boolean flag = false;
                for(int i = 0; i < this.width; i++)
                {
                    if(!this.get(i + (this.height - 1) * this.getWidth()).isEmpty())
                        flag = true;
                }
                if(flag)
                    break;
                this.height--;
            }
        }
        public Input getDelegate()
        {
            return this.delegate;
        }
        @Override
        public int getWidth()
        {
            return this.width;
        }
        @Override
        public int getHeight()
        {
            return this.height;
        }
        @Override
        public ItemStack get(int index)
        {
            int x = this.xBegin + index % this.getWidth();
            int y = this.yBegin + index / this.getWidth();
            return this.getDelegate().get(x + y * this.getDelegate().getWidth());
        }
    }
    class InputV2100 extends RecipeMojangAbstract.InputV2100<RecipeInputCraftingV2100> implements Input
    {
        public InputV2100(RecipeInputCraftingV2100 delegate)
        {
            super(delegate);
        }
        @Override
        public int getWidth()
        {
            return this.getDelegate().getWidth();
        }
        @Override
        public int getHeight()
        {
            return this.getDelegate().getHeight();
        }
    }

    @VersionRange(begin = 1903)
    @CompoundOverride(parent = RecipeCrafting.class, method = "getCategoryV1903")
    default RecipeCraftingCategoryV1903 getCategoryV1903$impl()
    {
        return this.getCategoryV1903();
    }

    @Override
    @CompoundOverride(parent = RecipeCrafting.class, method = "getRemainders0V2102")
    default DefaultedListV1100<?> getRemainders0V2102(RecipeInputCraftingV2100 input)
    {
        return this.getDisplay().getRemainders0V2102(input);
    }
}
