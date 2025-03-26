package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cWindowItems;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cWindowSlotUpdate;
import mz.mzlib.module.MzModule;

public abstract class EventAsyncPlayerDisplayItemInWindow<P extends Packet> extends EventAsyncPlayerDisplayItem<P>
{
    public int syncId;
    public int slotIndex;
    public EventAsyncPlayerDisplayItemInWindow(PacketEvent.Specialized<P> packetEvent, ItemStack original, int syncId, int slotIndex)
    {
        super(packetEvent, original);
        this.syncId = syncId;
        this.slotIndex = slotIndex;
    }
    
    public int getSyncId()
    {
        return this.syncId;
    }
    /**
     * The slot index of the item
     * or -1 if it's the cursor V1701
     */
    public int getSlotIndex()
    {
        return this.slotIndex;
    }
    
    @Override
    public void call()
    {
        super.call();
    }
    
    public static class ByPacketS2cWindowSlotUpdate extends EventAsyncPlayerDisplayItemInWindow<PacketS2cWindowSlotUpdate>
    {
        public ByPacketS2cWindowSlotUpdate(PacketEvent.Specialized<PacketS2cWindowSlotUpdate> packetEvent, ItemStack original, int syncId)
        {
            super(packetEvent, original, syncId, packetEvent.getPacket().getSlotIndex());
        }
        
        @Override
        public ItemStack getItemStack()
        {
            return this.getPacket().getItemStack();
        }
        @Override
        public void setItemStack(ItemStack value)
        {
            this.packetEvent.ensureCopied();
            this.getPacket().setItemStack(value);
        }
    }
    
    public static class ByPacketS2cWindowItems extends EventAsyncPlayerDisplayItemInWindow<PacketS2cWindowItems>
    {
        public ByPacketS2cWindowItems(PacketEvent.Specialized<PacketS2cWindowItems> packetEvent, ItemStack original, int syncId, int slotIndex)
        {
            super(packetEvent, original, syncId, slotIndex);
        }
        
        @Override
        public ItemStack getItemStack()
        {
            if(this.getSlotIndex()==-1)
                return this.getPacket().getCursorV1701();
            else
                return this.getPacket().getContents().get(this.getSlotIndex());
        }
        @Override
        public void setItemStack(ItemStack value)
        {
            this.packetEvent.ensureCopied();
            if(this.getSlotIndex()==-1)
                this.getPacket().setCursorV1701(value);
            else
                this.getPacket().getContents().set(this.getSlotIndex(), value);
        }
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventAsyncPlayerDisplayItemInWindow.class);
            this.register(new PacketListener<>(PacketS2cWindowSlotUpdate.FACTORY, packetEvent->new ByPacketS2cWindowSlotUpdate(packetEvent, packetEvent.getPacket().getItemStack(), packetEvent.getPacket().getSyncId()).call()));
            this.register(new PacketListener<>(PacketS2cWindowItems.FACTORY, packetEvent->
            {
                for(int i = 0; i<packetEvent.getPacket().getContents().size(); i++)
                {
                    new ByPacketS2cWindowItems(packetEvent, packetEvent.getPacket().getContents().get(i), packetEvent.getPacket().getSyncId(), i).call();
                }
            }));
            if(MinecraftPlatform.instance.getVersion()>=1701)
                this.register(new PacketListener<>(PacketS2cWindowItems.FACTORY, packetEvent->new ByPacketS2cWindowItems(packetEvent, packetEvent.getPacket().getCursorV1701(), packetEvent.getPacket().getSyncId(), -1).call()));
        }
    }
}
