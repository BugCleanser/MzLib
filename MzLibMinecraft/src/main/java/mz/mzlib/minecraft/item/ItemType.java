package mz.mzlib.minecraft.item;

import java.util.Objects;

/**
 * Think of stackable ItemStack as the same type
 */
public class ItemType
{
    public ItemStack itemStack;
    public ItemType(ItemStack itemStack)
    {
        this.itemStack = new ItemStackBuilder(ItemStack.copy(itemStack)).setCount(1).get();
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
