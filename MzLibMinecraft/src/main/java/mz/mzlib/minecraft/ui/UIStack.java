package mz.mzlib.minecraft.ui;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.EventPlayerJoin;
import mz.mzlib.minecraft.event.player.EventPlayerQuit;
import mz.mzlib.module.MzModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UIStack
{
    public static Map<EntityPlayer, UIStack> uiStacks = new ConcurrentHashMap<>();
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(new EventListener<>(EventPlayerJoin.class, Priority.VERY_VERY_HIGH, event->
            {
                uiStacks.put(event.getPlayer(), new UIStack(event.getPlayer()));
                if(event.isCancelled())
                    uiStacks.remove(event.getPlayer());
            }));
            for(EntityPlayer player: MinecraftServer.instance.getPlayers())
            {
                uiStacks.put(player, new UIStack(player));
            }
            this.register(new EventListener<>(EventPlayerQuit.class, Priority.VERY_VERY_LOW, event->uiStacks.remove(event.getPlayer())));
        }
    }
    
    public static UIStack get(EntityPlayer player)
    {
        return uiStacks.get(player);
    }
    
    public EntityPlayer player;
    public List<UI> data = new ArrayList<>();
    public UIStack(EntityPlayer player)
    {
        this.player = player;
    }
    
    public void start(UI ui)
    {
        this.data.clear();
        this.go(ui);
    }
    
    public void go(UI ui)
    {
        this.data.add(ui);
        ui.open(this.player);
    }
    
    public void back()
    {
        if(this.data.size()==1)
            this.data.get(0).close(this.player);
        this.data.remove(this.data.size()-1);
        if(!this.data.isEmpty())
            this.data.get(this.data.size()-1).open(this.player);
    }
    
    public UI top()
    {
        if(this.data.isEmpty())
            return null;
        return this.data.get(this.data.size()-1);
    }
    
    public void clear()
    {
        this.data.clear();
    }
    
    public void close()
    {
        if(this.data.isEmpty())
            return;
        this.data.get(this.data.size()-1).close(this.player);
        this.clear();
    }
}
