package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.text.Text;

import java.util.Arrays;

public class ItemStackBuilder
{
    public ItemStack result;
    public ItemStackBuilder(ItemStack itemStack)
    {
        this.result = itemStack.copy();
    }
    public ItemStackBuilder(Item item, int count)
    {
        this.result = ItemStack.newInstance(item, count);
    }
    public ItemStackBuilder(Item item)
    {
        this(item, 1);
    }
    
    public ItemStackBuilder(Identifier item, int count)
    {
        this(Item.fromId(item), count);
    }
    public ItemStackBuilder(Identifier item)
    {
        this(item, 1);
    }
    
    public ItemStackBuilder(String item, int count)
    {
        this(Identifier.newInstance(item), count);
    }
    public ItemStackBuilder(String item)
    {
        this(item, 1);
    }
    
    public ItemStackBuilder setCustomName(Text value)
    {
        Item.setCustomName(result, value);
        return this;
    }
    
    public ItemStackBuilder setLore(Text... value)
    {
        Item.setLore(result, Arrays.asList(value));
        return this;
    }
    
    public ItemStack build()
    {
        return result.copy();
    }
}
