package mz.mzlib.minecraft.event.window.async;

import mz.mzlib.minecraft.event.player.async.EventAsyncByPacket;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sWindowAction;
import mz.mzlib.minecraft.window.WindowAction;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.module.MzModule;

public class EventAsyncWindowAction extends EventAsyncWindow<PacketC2sWindowAction> implements EventAsyncByPacket.Cancellable
{
    public EventAsyncWindowAction(PacketEvent.Specialized<PacketC2sWindowAction> packetEvent)
    {
        super(packetEvent);
    }

    @Override
    public int getSyncId()
    {
        return this.getPacket().getSyncId();
    }

    public WindowActionType getActionType()
    {
        return this.getPacket().getActionType();
    }
    public void setActionType(WindowActionType value)
    {
        this.getPacketEvent().setPacket(PacketC2sWindowAction.builder().from(this.getPacket()).actionType(value).build());
    }

    public int getSlotIndex()
    {
        return this.getPacket().getSlotIndex();
    }
    public void setSlotIndex(int value)
    {
        this.getPacketEvent().setPacket(PacketC2sWindowAction.builder().from(this.getPacket()).slotIndex(value).build());
    }

    public int getData()
    {
        return this.getPacket().getData();
    }
    public void setData(int value)
    {
        this.getPacketEvent().setPacket(PacketC2sWindowAction.builder().from(this.getPacket()).data(value).build());
    }

    public WindowAction getAction()
    {
        return new WindowAction(this.getPlayer(), this.getSlotIndex(), this.getActionType(), this.getData());
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
            this.register(EventAsyncWindowAction.class);

            this.register(new PacketListener<>(
                PacketC2sWindowAction.FACTORY,
                packetEvent -> new EventAsyncWindowAction(packetEvent).call()
            ));
        }
    }
}
