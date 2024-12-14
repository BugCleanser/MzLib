package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.entity.EventEntity;
import mz.mzlib.module.MzModule;

public abstract class EventPlayer extends EventEntity
{
    public EventPlayer(EntityPlayer player)
    {
        super(player);
    }

    @Override
    public EntityPlayer getEntity()
    {
        return (EntityPlayer) super.getEntity();
    }
    
    @Override
    public void call()
    {
    }
    
    public static class ModuleEventPlayer extends MzModule
    {
        public static ModuleEventPlayer instance=new ModuleEventPlayer();
    
        @Override
        public void onLoad()
        {
            this.register(EventPlayer.class);
            
            this.register(EventPlayerQuit.Module.instance);
            this.register(EventPlayerChat.Module.instance);
            this.register(EventPlayerMoveAsync.Module.instance);
        }
    }
}
