package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.inventory.slot.Slot", end=1400), @VersionName(name="net.minecraft.container.Slot", begin=1400, end=1600), @VersionName(name="net.minecraft.screen.slot.Slot", begin=1600)})
public interface WindowSlot extends WrapperObject
{
    @WrapperCreator
    static WindowSlot create(Object wrapped)
    {
        return WrapperObject.create(WindowSlot.class, wrapped);
    }
    
    @WrapConstructor
    WindowSlot staticNewInstance(Inventory inventory, int index, int x, int y);
    static WindowSlot newInstance(Inventory inventory, int index, int x, int y)
    {
        return create(null).staticNewInstance(inventory, index, x, y);
    }
    static WindowSlot newInstance(Inventory inventory, int index)
    {
        return newInstance(inventory, index, 0, 0);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getStack"))
    ItemStack getItemStack();
    @WrapMinecraftMethod({@VersionName(name="setStack", end=1900), @VersionName(name="setStackNoCallbacks", begin = 1900)})
    void setItemStack(ItemStack itemStack);
    
    @WrapMinecraftMethod(@VersionName(name="canTakeItems"))
    boolean canTake(AbstractEntityPlayer player);
    @WrapMinecraftMethod(@VersionName(name="canTakePartial", begin=1700))
    boolean canTakePartialV1700(AbstractEntityPlayer player);
    
    @WrapMinecraftMethod(@VersionName(name="canInsert"))
    boolean canPlace(ItemStack itemStack);
    
    @WrapMinecraftMethod({@VersionName(name="getMaxStackAmount", end=1602), @VersionName(name="getMaxItemCount", begin=1602)})
    int getMaxStackCount();
    @WrapMinecraftMethod({@VersionName(name="getMaxStackAmount", end=1602), @VersionName(name="getMaxItemCount", begin=1602)})
    int getMaxStackCount(ItemStack itemStack);
    
    @WrapMinecraftMethod(@VersionName(name="onTakeItem"))
    void onTake(AbstractEntityPlayer player, ItemStack itemStack);
    
    default void setItemStackByPlayer(ItemStack itemStack)
    {
        this.setItemStack(itemStack);
    }
    @SpecificImpl("setItemStackByPlayer")
    @WrapMinecraftMethod(@VersionName(name="setStack", begin=1900))
    void setItemStackByPlayerV1900(ItemStack itemStack);
    
    @WrapMinecraftMethod(@VersionName(name="markDirty"))
    void markDirty();
}
