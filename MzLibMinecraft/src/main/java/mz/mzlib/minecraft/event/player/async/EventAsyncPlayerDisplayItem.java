package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;

public abstract class EventAsyncPlayerDisplayItem<P extends Packet> extends EventAsyncByPacket<P>
{
    public ItemStack original;
    
    public EventAsyncPlayerDisplayItem(PacketEvent.Specialized<P> packetEvent, ItemStack original)
    {
        super(packetEvent);
        this.original=original;
    }
    
    public abstract ItemStack getItemStack();
    public abstract void setItemStack(ItemStack value);
    
    public ItemStack getOriginal()
    {
        return this.original;
    }
    public ItemStack modifyItemStack()
    {
        if(this.original.getWrapped()==this.getItemStack().getWrapped())
            this.setItemStack(ItemStack.copy(this.getItemStack()));
        return this.getItemStack();
    }
    
    @Override
    public void setCancelled(boolean cancelled)
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void call()
    {
        super.call();
    }
}
