package mz.mzlib.minecraft.item;

import java.util.Objects;

/**
 * Think of stackable ItemStack as the same type
 */
public class ItemType
{
    ItemStack itemStack;
    public ItemType(ItemStack itemStack)
    {
        this.itemStack = itemStack.copy();
        this.itemStack.setCount(1);
    }

    public ItemStack toItemStack(int count)
    {
        ItemStack result = this.itemStack.copy();
        result.setCount(count);
        return result;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.itemStack);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof ItemType))
            return false;
        return Objects.equals(this.itemStack, ((ItemType) obj).itemStack);
    }
}
