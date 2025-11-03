package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.entity.EventEntity;
import mz.mzlib.minecraft.event.player.async.*;
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
        super.call();
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventPlayer.class);
            
            this.register(EventPlayerJoin.Module.instance);
            this.register(EventPlayerQuit.Module.instance);
            
            this.register(EventPlayerUseItem.Module.instance);
            
            this.register(EventAsyncByPacket.class);
            
            this.register(EventAsyncPlayerChat.Module.instance);
            this.register(EventAsyncPlayerMove.Module.instance);
            
            this.register(EventAsyncPlayerDisplayItem.class);
            this.register(EventAsyncPlayerDisplayItemInWindow.Module.instance);
            this.register(EventAsyncPlayerDisplayItemInEntity.Module.instance);
        }
    }
}
