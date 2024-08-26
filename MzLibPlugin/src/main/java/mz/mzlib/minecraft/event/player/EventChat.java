package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sChatMessage;
import mz.mzlib.module.MzModule;

public class EventChat extends EventPlayer
{
    public String message;
    public EventChat(EntityPlayer player, String message)
    {
        super(player);
        this.message=message;
    }

    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public void call()
    {
    }

    public static class Module extends MzModule
    {
        public static Module instance=new Module();

        @Override
        public void onLoad()
        {
            this.register(EventChat.class);
            this.register(new PacketListener<>(PacketC2sChatMessage.class, true, (e, p)->
            {
                EventChat event=new EventChat(e.getPlayer(), p.getChatMessage());
                event.setCancelled(e.isCancelled());
                event.call();
                e.setCancelled(event.isCancelled());
                p.setChatMessage(event.getMessage());
                e.runLater(event::complete);
            }));
        }
    }
}
