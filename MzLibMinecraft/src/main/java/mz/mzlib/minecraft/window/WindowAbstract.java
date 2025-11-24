package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.inventory.BukkitInventoryView;
import mz.mzlib.minecraft.bukkit.inventory.CraftInventoryView;
import mz.mzlib.minecraft.entity.player.EntityPlayerAbstract;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.CompoundSuper;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;

@Compound
public interface WindowAbstract extends Window
{
    WrapperFactory<WindowAbstract> FACTORY = WrapperFactory.of(WindowAbstract.class);

    Inventory getInventory();

    EntityPlayerAbstract getPlayer();

    @Override
    @CompoundOverride(parent = Window.class, method = "getBukkitView")
    default BukkitInventoryView getBukkitView()
    {
        return CraftInventoryView.newInstance(this.getPlayer(), this.getInventory(), this);
    }

    @Override
    @CompoundOverride(parent = Window.class, method = "quickMove")
    default ItemStack quickMove(EntityPlayerAbstract player, int index)
    {
        WindowSlot slot = this.getSlot(index);
        if(!slot.isPresent() || slot.getItemStack().isEmpty())
            return ItemStack.empty();
        ItemStack is = slot.getItemStack();
        ItemStack original = is.copy();
        int upperSize = this.getSlots().size() - 36;
        if(index < upperSize)
        {
            if(!this.placeIn(is, upperSize, this.getSlots().size(), true))
                return ItemStack.empty();
        }
        else if(!this.placeIn(is, 0, upperSize, false))
            return ItemStack.empty();

        if(is.isEmpty())
            slot.setItemStackByPlayer(ItemStack.empty());
        else
            slot.markDirty();
        return original;
    }

    @Override
    @CompoundOverride(parent = Window.class, method = "checkReachable")
    default boolean checkReachable(EntityPlayerAbstract player)
    {
        return true;
    }

    /**
     * @param index the index of slot or -1 when click title bar with item or -999 when click outside
     * @param data  see {@link WindowActionType}
     */
    default void onAction(int index, int data, WindowActionType actionType, EntityPlayerAbstract player)
    {
        this.onActionSuper(index, data, actionType, player);
    }

    @CompoundOverride(parent = Window.class, method = "onActionV_1700")
    @Override
    default ItemStack onActionV_1700(int index, int data, WindowActionType actionType, EntityPlayerAbstract player)
    {
        this.onAction(index, data, actionType, player);
        if(player.isInstanceOf(EntityPlayer.FACTORY))
            player.castTo(EntityPlayer.FACTORY).updateWindowV_1700(this);
        return ItemStack.empty();
    }

    @CompoundOverride(parent = Window.class, method = "onActionV1700")
    @Override
    default void onActionV1700(int index, int data, WindowActionType actionType, EntityPlayerAbstract player)
    {
        this.onAction(index, data, actionType, player);
    }

    void onActionSuper(int index, int data, WindowActionType actionType, EntityPlayerAbstract player);

    @CompoundSuper(parent = Window.class, method = "onActionV_1700")
    ItemStack onActionSuperV_1700(int index, int data, WindowActionType actionType, EntityPlayerAbstract player);

    @SpecificImpl("onActionSuper")
    @VersionRange(end = 1700)
    default void onActionSuperImplV_1700(int index, int data, WindowActionType actionType, EntityPlayerAbstract player)
    {
        ItemStack ignored = this.onActionSuperV_1700(index, data, actionType, player);
    }

    @SpecificImpl("onActionSuper")
    @VersionRange(begin = 1700)
    @CompoundSuper(parent = Window.class, method = "onActionV1700")
    void onActionSuperV1700(int index, int data, WindowActionType actionType, EntityPlayerAbstract player);
}
