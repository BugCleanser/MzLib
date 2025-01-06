package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.module.MzModule;

public class EventPlayerJoin extends EventPlayer
{
    public EventPlayerJoin(EntityPlayer player)
    {
        super(player);
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
    
    public static class Module extends MzModule
    {
        public static Module instance=new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventPlayerJoin.class);
            // TODO
        }
    }
}
