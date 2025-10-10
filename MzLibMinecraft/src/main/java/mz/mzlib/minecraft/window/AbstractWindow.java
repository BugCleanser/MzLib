package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.inventory.BukkitInventoryView;
import mz.mzlib.minecraft.bukkit.inventory.CraftInventoryView;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.CompoundSuper;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@Compound
public interface AbstractWindow extends Window
{
    WrapperFactory<AbstractWindow> FACTORY = WrapperFactory.of(AbstractWindow.class);
    @Deprecated
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
        if(!slot.isPresent() || ItemStack.isEmpty(slot.getItemStack()))
            return ItemStack.empty();
        ItemStack is = slot.getItemStack();
        ItemStack original = ItemStack.copy(is);
        int upperSize = this.getSlots().size()-36;
        if(index<upperSize)
        {
            if(!this.placeIn(is, upperSize, this.getSlots().size(), true))
                return ItemStack.empty();
        }
        else if(!this.placeIn(is, 0, upperSize, false))
            return ItemStack.empty();
        
        if(ItemStack.isEmpty(is))
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
    
    /**
     * @param index the index of slot or -1 when click title bar with item or -999 when click outside
     * @param data  see {@link WindowActionType}
     */
    default void onAction(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        this.onActionSuper(index, data, actionType, player);
    }
    
    @CompoundOverride(parent=Window.class, method="onActionV_1700")
    @Override
    default ItemStack onActionV_1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        this.onAction(index, data, actionType, player);
        if(player.isInstanceOf(EntityPlayer.FACTORY))
            player.castTo(EntityPlayer.FACTORY).updateWindowV_1700(this);
        return ItemStack.empty();
    }
    
    @CompoundOverride(parent=Window.class, method="onActionV1700")
    @Override
    default void onActionV1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        this.onAction(index, data, actionType, player);
    }
    
    void onActionSuper(int index, int data, WindowActionType actionType, AbstractEntityPlayer player);
    
    @CompoundSuper(parent=Window.class, method="onActionV_1700")
    ItemStack onActionSuperV_1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player);
    
    @SpecificImpl("onActionSuper")
    @VersionRange(end=1700)
    default void onActionSuperImplV_1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        ItemStack ignored = this.onActionSuperV_1700(index, data, actionType, player);
    }
    
    @SpecificImpl("onActionSuper")
    @VersionRange(begin=1700)
    @CompoundSuper(parent=Window.class, method="onActionV1700")
    void onActionSuperV1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player);
}
