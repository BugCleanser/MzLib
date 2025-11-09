package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Editor;

public abstract class EventAsyncPlayerDisplayItem<P extends Packet> extends EventAsyncByPacket<P>
{
    public ItemStack original;

    public EventAsyncPlayerDisplayItem(PacketEvent.Specialized<? extends P> packetEvent, ItemStack original)
    {
        super(packetEvent);
        this.original = original;
    }
    public EventAsyncPlayerDisplayItem(PacketEvent.Specialized<? extends P> packetEvent)
    {
        super(packetEvent);
        this.original = this.getItemStack();
    }

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
            this.setItemStack(ItemStack.copy(this.getItemStack()));
        return this.getItemStack();
    }

    @Override
    public void call()
    {
        super.call();
    }
}
