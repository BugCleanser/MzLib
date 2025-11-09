package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cCursorItemV2102;
import mz.mzlib.module.MzModule;

public class EventAsyncPlayerDisplayItemInCursorV2102 extends EventAsyncPlayerDisplayItem<PacketS2cCursorItemV2102>
{
    public EventAsyncPlayerDisplayItemInCursorV2102(PacketEvent.Specialized<PacketS2cCursorItemV2102> packetEvent)
    {
        super(packetEvent);
    }

    @Override
    public ItemStack getItemStack()
    {
        return this.getPacketEvent().getPacket().getValue();
    }
    @Override
    public void setItemStack(ItemStack value)
    {
        this.getPacketEvent().setPacket(PacketS2cCursorItemV2102.newInstance(value));
    }

    @Override
    public void call()
    {
        super.call();
    }

    public static class Module extends MzModule
    {
        public static Module instance = new Module();

        @Override
        public void onLoad()
        {
            this.register(EventAsyncPlayerDisplayItemInCursorV2102.class);
            if(MinecraftPlatform.instance.getVersion() < 2102)
                return;

            this.register(new PacketListener<>(
                PacketS2cCursorItemV2102.FACTORY,
                packetEvent -> new EventAsyncPlayerDisplayItemInCursorV2102(packetEvent).call()
            ));
        }
    }
}
