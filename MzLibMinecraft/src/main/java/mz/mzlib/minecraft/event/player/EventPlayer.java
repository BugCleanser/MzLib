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
        return (EntityPlayer)super.getEntity();
    }
    
    public EntityPlayer getPlayer()
    {
        return this.getEntity();
    }
    
    @Override
    public void call()
    {
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventPlayer.class);
            
            this.register(EventPlayerQuit.Module.instance);
            
            this.register(EventPlayerByPacket.class);
            
            this.register(EventPlayerChatAsync.Module.instance);
            this.register(EventPlayerMove.Module.instance);
            
            this.register(EventPlayerDisplayItem.class);
            this.register(EventPlayerDisplayItemInWindow.Module.instance);
        }
    }
}
