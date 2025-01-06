package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.PacketEvent;

public abstract class EventPlayerDisplayItem extends EventPlayerByPacket
{
    public EventPlayerDisplayItem(PacketEvent packetEvent)
    {
        super(packetEvent);
    }
    
    public abstract ItemStack getItemStack();
    public abstract void setItemStack(ItemStack value);
    
    public boolean modified=false;
    public void modify()
    {
        if(this.modified)
            return;
        this.setItemStack(this.getItemStack().copy());
        this.modified=true;
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
