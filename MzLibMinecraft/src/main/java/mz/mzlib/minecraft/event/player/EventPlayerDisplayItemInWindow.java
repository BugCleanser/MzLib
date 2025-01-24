package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cWindowItems;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cWindowSlotUpdate;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.module.MzModule;

public abstract class EventPlayerDisplayItemInWindow<P extends Packet> extends EventPlayerDisplayItem<P>
{
    public Window window;
    public int slotIndex;
    public EventPlayerDisplayItemInWindow(PacketEvent.Specialized<P> packetEvent, ItemStack original, Window window, int slotIndex)
    {
        super(packetEvent, original);
        this.window = window;
        this.slotIndex = slotIndex;
    }
    
    public Window getWindow()
    {
        return this.window;
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
    
    public static class ByPacketS2cWindowSlotUpdate extends EventPlayerDisplayItemInWindow<PacketS2cWindowSlotUpdate>
    {
        public ByPacketS2cWindowSlotUpdate(PacketEvent.Specialized<PacketS2cWindowSlotUpdate> packetEvent, ItemStack original, Window window)
        {
            super(packetEvent, original, window, packetEvent.getPacket().getSlotIndex());
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
    
    public static class ByPacketS2cWindowItems extends EventPlayerDisplayItemInWindow<PacketS2cWindowItems>
    {
        public ByPacketS2cWindowItems(PacketEvent.Specialized<PacketS2cWindowItems> packetEvent, ItemStack original, Window window, int slotIndex)
        {
            super(packetEvent, original, window, slotIndex);
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
            this.register(EventPlayerDisplayItemInWindow.class);
            this.register(new PacketListener<>(PacketS2cWindowSlotUpdate::create, packetEvent->packetEvent.sync(()->
            {
                Window window = packetEvent.getPlayer().getWindow(packetEvent.getPacket().getSyncId());
                if(!window.isPresent())
                    return;
                new ByPacketS2cWindowSlotUpdate(packetEvent, packetEvent.getPacket().getItemStack(), window).call();
            })));
            this.register(new PacketListener<>(PacketS2cWindowItems::create, packetEvent->packetEvent.sync(()->
            {
                Window window = packetEvent.getPlayer().getWindow(packetEvent.getPacket().getSyncId());
                if(!window.isPresent())
                    return;
                for(int i = 0; i<packetEvent.getPacket().getContents().size(); i++)
                {
                    new ByPacketS2cWindowItems(packetEvent, packetEvent.getPacket().getContents().get(i), window, i).call();
                }
            })));
            if(MinecraftPlatform.instance.getVersion()>=1701)
                this.register(new PacketListener<>(PacketS2cWindowItems::create, packetEvent->packetEvent.sync(()->
                {
                    Window window = packetEvent.getPlayer().getWindow(packetEvent.getPacket().getSyncId());
                    if(!window.isPresent())
                        return;
                    new ByPacketS2cWindowItems(packetEvent, packetEvent.getPacket().getCursorV1701(), window, -1).call();
                })));
        }
    }
}
