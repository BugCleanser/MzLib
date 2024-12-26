package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cWindowItems;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cWindowSlotUpdate;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.module.MzModule;

public abstract class EventPlayerDisplayItemInWindowAsync extends EventPlayerDisplayItemAsync
{
    public Window window;
    public int slotIndex;
    public EventPlayerDisplayItemInWindowAsync(PacketEvent packetEvent, Window window, int slotIndex)
    {
        super(packetEvent);
        this.window=window;
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
    
    public static class ByPacketS2cWindowSlotUpdate extends EventPlayerDisplayItemInWindowAsync
    {
        public PacketS2cWindowSlotUpdate packet;
        public ByPacketS2cWindowSlotUpdate(PacketEvent packetEvent, Window window, PacketS2cWindowSlotUpdate packet)
        {
            super(packetEvent, window, packet.getSlotIndex());
            this.packet=packet;
        }
        
        @Override
        public ItemStack getItemStack()
        {
            return this.packet.getItemStack();
        }
        @Override
        public void setItemStack(ItemStack value)
        {
            this.packet.setItemStack(value);
        }
    }
    
    public static class ByPacketS2cWindowItems extends EventPlayerDisplayItemInWindowAsync
    {
        public PacketS2cWindowItems packet;
        public ByPacketS2cWindowItems(PacketEvent packetEvent, Window window, int slotIndex, PacketS2cWindowItems packet)
        {
            super(packetEvent, window, slotIndex);
            this.packet=packet;
        }
        
        @Override
        public ItemStack getItemStack()
        {
            if(this.getSlotIndex()==-1)
                return this.packet.getCursorV1701();
            else
                return this.packet.getContents().get(this.getSlotIndex());
        }
        @Override
        public void setItemStack(ItemStack value)
        {
            if(this.getSlotIndex()==-1)
                this.packet.setCursorV1701(value);
            else
                this.packet.getContents().set(this.getSlotIndex(), value);
        }
    }
    
    public static class Module extends MzModule
    {
        public static Module instance=new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventPlayerDisplayItemInWindowAsync.class);
            this.register(new PacketListener<>(PacketS2cWindowSlotUpdate.class, (event, packet) ->
            {
                Window window=event.getPlayer().getCurrentWindow();
                if(window.getSyncId()!=packet.getSyncId())
                    return;
                ByPacketS2cWindowSlotUpdate e=new ByPacketS2cWindowSlotUpdate(event, window, packet);
                e.call();
                event.whenComplete(e::complete);
            }));
            this.register(new PacketListener<>(PacketS2cWindowItems.class, (event, packet) ->
            {
                Window window=event.getPlayer().getCurrentWindow();
                if(window.getSyncId()!=packet.getSyncId())
                    return;
                for(int i=0; i<packet.getContents().size(); i++)
                {
                    ByPacketS2cWindowItems e=new ByPacketS2cWindowItems(event, window, i, packet);
                    e.call();
                    event.whenComplete(e::complete);
                }
            }));
            if(MinecraftPlatform.instance.getVersion()>=1701)
                this.register(new PacketListener<>(PacketS2cWindowItems.class, (event, packet) ->
                {
                    Window window=event.getPlayer().getCurrentWindow();
                    if(window.getSyncId()!=packet.getSyncId())
                        return;
                    ByPacketS2cWindowItems e=new ByPacketS2cWindowItems(event, window, -1, packet);
                    e.call();
                    event.whenComplete(e::complete);
                }));
        }
    }
}
