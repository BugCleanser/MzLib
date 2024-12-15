package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ItemStackBuilder
{
    public Item item;
    public int count;
    public ItemStackBuilder(Item item, int count)
    {
        this.item = item;
        this.count = count;
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
    
    public List<Function<ItemStack, ItemStack>> operations = new ArrayList<>();
    
    public ItemStackBuilder addOperation(Function<ItemStack, ItemStack> operation)
    {
        this.operations.add(operation);
        return this;
    }
    
    public ItemStackBuilder(ItemStack itemStack)
    {
        addOperation(is->itemStack.copy());
    }
    
    public ItemStackBuilder setDamage(int damage)
    {
        return this.addOperation(itemStack->
        {
            itemStack.setDamageV_1300(damage);
            return itemStack;
        });
    }
    
    public ItemStackBuilder setDisplayName(Text value)
    {
        return this.addOperation(itemStack->
        {
            Item.setDisplayName(itemStack, value);
            return itemStack;
        });
    }
    
    public ItemStack build()
    {
        ItemStack result = ItemStack.newInstance(item, count);
        for(Function<ItemStack, ItemStack> operation: operations)
            result = operation.apply(result);
        return result;
    }
}
