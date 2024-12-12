package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.bukkit.inventory.BukkitInventoryView;
import mz.mzlib.minecraft.bukkit.inventory.CraftInventoryView;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
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
    @CompoundOverride("getBukkitView")
    default BukkitInventoryView getBukkitView()
    {
        return CraftInventoryView.newInstance(this.getPlayer(), this);
    }
    
    @Override
    @CompoundOverride("quickMove")
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
    @CompoundOverride("checkReachable")
    default boolean checkReachable(AbstractEntityPlayer player)
    {
        return true;
    }
}
