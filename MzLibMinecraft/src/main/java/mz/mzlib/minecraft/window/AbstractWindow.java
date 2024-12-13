package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.inventory.BukkitInventoryView;
import mz.mzlib.minecraft.bukkit.inventory.CraftInventoryView;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.CompoundSuper;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@Compound
public interface AbstractWindow extends Window
{
    @WrapperCreator
    static AbstractWindow create(Object wrapped)
    {
        return WrapperObject.create(AbstractWindow.class, wrapped);
    }
    
    Inventory getInventory();
    
    AbstractEntityPlayer getPlayer();
    
    @Override
    @CompoundOverride(parent=Window.class, method="getBukkitView")
    default BukkitInventoryView getBukkitView()
    {
        return CraftInventoryView.newInstance(this.getPlayer(), this.getInventory(), this);
    }
    
    @Override
    @CompoundOverride(parent=Window.class, method="quickMove")
    default ItemStack quickMove(AbstractEntityPlayer player, int index)
    {
        WindowSlot slot = this.getSlot(index);
        if(!slot.isPresent() || slot.getItemStack().isEmpty())
            return ItemStack.empty();
        ItemStack is = slot.getItemStack();
        ItemStack original = is.copy();
        if(index<this.getInventory().size())
        {
            if(!this.placeIn(is, this.getInventory().size(), this.getSlots().size(), true))
                return ItemStack.empty();
        }
        else if(!this.placeIn(is, 0, this.getInventory().size(), false))
            return ItemStack.empty();
        
        if(is.isEmpty())
            slot.setItemStackByPlayer(ItemStack.empty());
        else
            slot.markDirty();
        
        return original;
    }
    
    @Override
    @CompoundOverride(parent=Window.class, method="checkReachable")
    default boolean checkReachable(AbstractEntityPlayer player)
    {
        return true;
    }
    
    default ItemStack onAction(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        return this.onActionSuper(index, data, actionType, player);
    }
    
    @CompoundOverride(parent=Window.class, method="onActionV_1700")
    @Override
    default ItemStack onActionV_1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        return this.onAction(index, data, actionType, player);
    }
    @CompoundOverride(parent=Window.class, method="onActionV1700")
    @Override
    default void onActionV1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        this.onAction(index, data, actionType, player);
    }
    
    ItemStack onActionSuper(int index, int data, WindowActionType actionType, AbstractEntityPlayer player);
    @CompoundSuper(parent=Window.class, method="onActionV_1700")
    ItemStack onActionSuperV_1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player);
    @SpecificImpl("onActionSuper")
    @VersionRange(end=1700)
    default ItemStack onActionSuperImplV_1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        return this.onActionSuperV_1700(index, data, actionType, player);
    }
    @CompoundSuper(parent=Window.class, method="onActionV1700")
    void onActionSuperV1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player);
    @SpecificImpl("onActionSuper")
    @VersionRange(begin=1700)
    default ItemStack onActionSuperImplV1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        this.onActionSuperV1700(index, data, actionType, player);
        return null;
    }
}
