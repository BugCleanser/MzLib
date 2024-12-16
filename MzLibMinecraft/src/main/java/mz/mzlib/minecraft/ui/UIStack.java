package mz.mzlib.minecraft.ui;

import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.EventPlayerQuit;
import mz.mzlib.module.MzModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UIStack
{
    public static Map<EntityPlayer, UIStack> uiStacks=new ConcurrentHashMap<>();
    
    public static class Module extends MzModule
    {
        public static Module instance=new Module();
        
        @Override
        public void onLoad()
        {
            this.register(new EventListener<>(EventPlayerQuit.class, e -> uiStacks.remove(e.getEntity())));
        }
    }
    
    // TODO: remove
    public static UIStack get(EntityPlayer player)
    {
        return uiStacks.computeIfAbsent(player, UIStack::new);
    }
    
    public EntityPlayer player;
    public List<UI> data=new ArrayList<>();
    public UIStack(EntityPlayer player)
    {
        this.player=player;
    }
    
    public synchronized void start(UI ui)
    {
        this.data.clear();
        this.go(ui);
    }
    
    public synchronized void go(UI ui)
    {
        this.data.add(ui);
        ui.open(this.player);
    }
    
    public synchronized void back()
    {
        this.data.get(this.data.size()-1).close(this.player);
        this.data.remove(this.data.size()-1);
        if(!this.data.isEmpty())
            this.data.get(this.data.size()-1).open(this.player);
    }
    
    public synchronized void clear()
    {
        this.data.clear();
    }
    
    public synchronized void close()
    {
        if(this.data.isEmpty())
            return;
        this.data.get(this.data.size()-1).close(this.player);
        this.clear();
    }
}
