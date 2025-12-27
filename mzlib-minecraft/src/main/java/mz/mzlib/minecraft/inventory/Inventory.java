package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Objects;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.inventory.Inventory"))
public interface Inventory extends WrapperObject
{
    WrapperFactory<Inventory> FACTORY = WrapperFactory.of(Inventory.class);
    @Deprecated
    @WrapperCreator
    static Inventory create(Object wrapped)
    {
        return WrapperObject.create(Inventory.class, wrapped);
    }

    @WrapMinecraftMethod({ @VersionName(name = "getInvSize", end = 1600), @VersionName(name = "size", begin = 1600) })
    int size();

    @WrapMinecraftMethod({
        @VersionName(name = "getInvStack", end = 1600),
        @VersionName(name = "getStack", begin = 1600)
    })
    ItemStack getItemStack(int index);

    @WrapMinecraftMethod({
        @VersionName(name = "setInvStack", end = 1600),
        @VersionName(name = "setStack", begin = 1600)
    })
    void setItemStack(int index, ItemStack itemStack);

    default boolean addItemStack(ItemStack itemStack)
    {
        if(itemStack.isEmpty())
            return false;
        boolean result = false;
        int size = this.size();
        for(int i = 0; i < size && !itemStack.isEmpty(); i++)
        {
            ItemStack cnt = this.getItemStack(i);
            if(ItemStack.isStackable(cnt, itemStack))
            {
                int count = Math.min(itemStack.getCount(), cnt.getMaxStackCount() - cnt.getCount());
                cnt.grow(count);
                itemStack.shrink(count);
            }
        }
        if(!itemStack.isEmpty())
            for(int i = 0; i < size; i++)
            {
                ItemStack is = this.getItemStack(i);
                if(is.isEmpty())
                {
                    this.setItemStack(i, itemStack.copy());
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
        for(int i = 0; i < size; i++)
        {
            this.setItemStack(i, ItemStack.EMPTY);
        }
    }

    default Slot slot(int index)
    {
        return new Slot(this, index);
    }
    class Slot
    {
        Inventory inventory;
        int index;

        public Slot(Inventory inventory, int index)
        {
            this.inventory = inventory;
            this.index = index;
        }

        public Inventory getInventory()
        {
            return this.inventory;
        }
        public int getIndex()
        {
            return this.index;
        }

        public ItemStack get()
        {
            return this.getInventory().getItemStack(this.getIndex());
        }
        public void set(ItemStack itemStack)
        {
            this.getInventory().setItemStack(this.getIndex(), itemStack);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(this.getInventory(), this.getIndex());
        }
        @Override
        public boolean equals(Object o)
        {
            if(this == o)
                return true;
            if(!(o instanceof Slot))
                return false;
            Slot slot = (Slot) o;
            return Objects.equals(this.getInventory(), slot.getInventory()) && this.getIndex() == slot.getIndex();
        }
    }


    @VersionRange(begin = 1300, end = 1400)
    @WrapMinecraftMethod(@VersionName(name = "method_11260"))
    int getWidthV1300_1400();
    @VersionRange(begin = 1300, end = 1400)
    @WrapMinecraftMethod(@VersionName(name = "method_11259"))
    int getHeightV1300_1400();
}
