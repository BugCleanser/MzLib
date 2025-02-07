package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.inventory.Inventory"))
public interface Inventory extends WrapperObject
{
    @WrapperCreator
    static Inventory create(Object wrapped)
    {
        return WrapperObject.create(Inventory.class, wrapped);
    }
    
    @WrapMinecraftMethod({@VersionName(name="getInvSize", end=1600), @VersionName(name="size", begin=1600)})
    int size();
    
    @WrapMinecraftMethod({@VersionName(name="getInvStack", end=1600), @VersionName(name="getStack", begin=1600)})
    ItemStack getItemStack(int index);
    
    @WrapMinecraftMethod({@VersionName(name="setInvStack", end=1600), @VersionName(name="setStack", begin=1600)})
    void setItemStack(int index, ItemStack itemStack);
    
    default boolean addItemStack(ItemStack itemStack)
    {
        if(ItemStack.isEmpty(itemStack))
            return false;
        boolean result = false;
        int size = this.size();
        for(int i = 0; i<size && !ItemStack.isEmpty(itemStack); i++)
        {
            ItemStack cnt = this.getItemStack(i);
            if(ItemStack.isStackable(cnt, itemStack))
            {
                int count = Math.min(itemStack.getCount(), cnt.getMaxStackCount()-cnt.getCount());
                cnt.grow(count);
                itemStack.shrink(count);
            }
        }
        if(!ItemStack.isEmpty(itemStack))
            for(int i = 0; i<size; i++)
            {
                ItemStack is = this.getItemStack(i);
                if(ItemStack.isEmpty(is))
                {
                    this.setItemStack(i, ItemStack.copy(itemStack));
                    itemStack.setCount(0);
                    result = true;
                    break;
                }
            }
        return result;
    }
    
    default void clear()
    {
        int size = this.size();
        for(int i = 0; i<size; i++)
        {
            this.setItemStack(i, ItemStack.empty());
        }
    }
}
