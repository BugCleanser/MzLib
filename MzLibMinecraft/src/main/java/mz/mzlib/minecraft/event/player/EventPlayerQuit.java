package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.module.MzModule;

// TODO: call
public class EventPlayerQuit extends EventPlayer
{
    public EventPlayerQuit(EntityPlayer player)
    {
        super(player);
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
            this.register(EventPlayerQuit.class);
        }
    }
}
