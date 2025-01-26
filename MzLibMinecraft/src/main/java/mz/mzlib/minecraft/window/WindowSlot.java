package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
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
    
    @WrapMinecraftFieldAccessor(@VersionName(name="inventory"))
    Inventory getInventory();
    @WrapMinecraftFieldAccessor(@VersionName(name="inventory"))
    void setInventory(Inventory value);
    
    /**
     * Get the index of the inventory
     */
    @WrapMinecraftFieldAccessor({@VersionName(name="invSlot", end=1600), @VersionName(name="index", begin=1600)})
    int getIndex();
    @WrapMinecraftFieldAccessor({@VersionName(name="invSlot", end=1600), @VersionName(name="index", begin=1600)})
    void setIndex(int value);
    
    /**
     * Get the x position of the client side
     */
    @WrapMinecraftFieldAccessor({@VersionName(name="x", end=1400), @VersionName(name="xPosition", begin=1400, end=1600), @VersionName(name="x", begin=1600)})
    int getX();
    @WrapMinecraftFieldAccessor({@VersionName(name="x", end=1400), @VersionName(name="xPosition", begin=1400, end=1600), @VersionName(name="x", begin=1600)})
    void setX(int value);
    
    /**
     * Get the x position of the client side
     */
    @WrapMinecraftFieldAccessor({@VersionName(name="y", end=1400), @VersionName(name="yPosition", begin=1400, end=1600), @VersionName(name="y", begin=1600)})
    int getY();
    @WrapMinecraftFieldAccessor({@VersionName(name="y", end=1400), @VersionName(name="yPosition", begin=1400, end=1600), @VersionName(name="y", begin=1600)})
    void setY(int value);
    
    /**
     * Get the index of the window
     */
    @WrapMinecraftFieldAccessor(@VersionName(name="id"))
    int getSlotIndex();
    @WrapMinecraftFieldAccessor(@VersionName(name="id"))
    void setSlotIndex(int value);
    
    static WindowSlot newInstance(Inventory inventory, int index)
    {
        return newInstance(inventory, index, 0, 0);
    }
    static WindowSlot newInstance(Inventory inventory, int index, int x, int y)
    {
        return create(null).staticNewInstance(inventory, index, x, y);
    }
    @WrapConstructor
    WindowSlot staticNewInstance(Inventory inventory, int index, int x, int y);
    
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
    
    /**
     * @return the item stack in the parameters
     */
    @VersionRange(end=1700)
    @WrapMinecraftMethod({@VersionName(name="method_3298", end=1400), @VersionName(name="onTakeItem", begin=1400)})
    ItemStack onTakeV_1700(AbstractEntityPlayer player, ItemStack itemStack);
    @WrapMinecraftMethod(@VersionName(name="onTakeItem", begin=1700))
    void onTakeV1700(AbstractEntityPlayer player, ItemStack itemStack);
    
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
