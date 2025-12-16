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

    default void onAction(WindowAction action)
    {
        this.onAction$super(action);
    }
    /**
     * @param index the index of slot or -1 when click title bar with item or -999 when click outside
     * @param data see {@link WindowActionType}
     * @see #onAction(WindowAction)
     */
    @Deprecated
    default void onAction(int index, int data, WindowActionType type, EntityPlayerAbstract player)
    {
        this.onAction(new WindowAction(player.as(EntityPlayer.FACTORY), index, type, data));
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

    void onAction$super(WindowAction action);

    @SpecificImpl("onAction$super")
    @VersionRange(end = 1700)
    default void onAction$superV_1700(WindowAction action)
    {
        ItemStack ignored = this.onAction$superV_1700(action.getIndex(), action.getData(), action.getType(), action.getPlayer());
    }
    @CompoundSuper(parent = Window.class, method = "onActionV_1700")
    ItemStack onAction$superV_1700(int index, int data, WindowActionType actionType, EntityPlayerAbstract player);

    @SpecificImpl("onAction$super")
    @VersionRange(begin = 1700)
    default void onAction$superV1700(WindowAction action)
    {
        this.onAction$superV1700(action.getIndex(), action.getData(), action.getType(), action.getPlayer());
    }
    @VersionRange(begin = 1700)
    @CompoundSuper(parent = Window.class, method = "onActionV1700")
    void onAction$superV1700(int index, int data, WindowActionType actionType, EntityPlayerAbstract player);
}
