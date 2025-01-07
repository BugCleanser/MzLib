package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.PacketEvent;

public abstract class EventPlayerDisplayItem extends EventPlayerByPacket
{
    public ItemStack original;
    
    public EventPlayerDisplayItem(PacketEvent packetEvent, ItemStack original)
    {
        super(packetEvent);
        this.original=original;
    }
    
    public abstract ItemStack getItemStack();
    public abstract void setItemStack(ItemStack value);
    
    public ItemStack modifyItemStack()
    {
        if(original.getWrapped()==this.getItemStack().getWrapped())
            this.setItemStack(this.getItemStack().copy());
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
