package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.EventPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.Editor;

public abstract class EventAsyncPlayerDisplayItem extends EventPlayer
{
    public ItemStack original;

    public EventAsyncPlayerDisplayItem(EntityPlayer player, ItemStack original)
    {
        super(player);
        this.original = original;
    }

    public abstract void sync(Runnable task);

    public abstract ItemStack getItemStack();
    public abstract void setItemStack(ItemStack value);

    public ItemStack getOriginal()
    {
        return this.original;
    }

    public Editor<ItemStack> reviseItemStack()
    {
        return Editor.ofClone(this::getItemStack, ItemStack::clone0, this::setItemStack);
    }
    /**
     * @see #reviseItemStack
     */
    @Deprecated
    public ItemStack modifyItemStack()
    {
        if(this.original.getWrapped() == this.getItemStack().getWrapped())
            this.setItemStack(this.getItemStack().copy());
        return this.getItemStack();
    }

    @Override
    public void call()
    {
        super.call();
    }
}
