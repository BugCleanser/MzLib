package mz.mzlib.minecraft.network.packet;

import io.netty.channel.Channel;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.util.Option;
import mz.mzlib.util.TaskList;

import java.util.function.Function;

public class PacketEvent
{
    public Channel channel;
    public Option<EntityPlayer> player;
    public Packet packet;
    public boolean isCancelled = false;
    public PacketEvent(Channel channel, Option<EntityPlayer> player, Packet packet)
    {
        this.channel = channel;
        this.player = player;
        this.packet = packet;
    }
    
    public Option<EntityPlayer> getPlayer()
    {
        return this.player;
    }
    
    public <T extends Packet> T getPacket(Function<Object, T> creator)
    {
        return this.packet.castTo(creator);
    }
    
    public boolean isCopied = false;
    public void ensureCopied()
    {
        if(this.isCopied)
            return;
        this.packet.setWrappedFrom(this.packet.copy(this.channel.alloc()));
        this.isCopied = true;
    }
    
    public TaskList syncTasks = null;
    public void sync(Runnable task)
    {
        if(this.syncTasks==null)
            this.syncTasks = new TaskList();
        this.syncTasks.schedule(task);
    }
    
    public void setCancelled(boolean cancelled)
    {
        this.isCancelled = cancelled;
    }
    public boolean isCancelled()
    {
        return this.isCancelled;
    }
    
    public <T extends Packet> Specialized<T> specialized(Function<Object, T> packetCreator)
    {
        return new Specialized<>(this, packetCreator);
    }
    
    public static class Specialized<T extends Packet>
    {
        public PacketEvent common;
        public Function<Object, T> packetCreator;
        public Specialized(PacketEvent common, Function<Object, T> packetCreator)
        {
            this.common = common;
            this.packetCreator = packetCreator;
        }
        
        public Option<EntityPlayer> getPlayer()
        {
            return this.common.getPlayer();
        }
        
        public T getPacket()
        {
            return this.common.getPacket(this.packetCreator);
        }
        
        public void ensureCopied()
        {
            this.common.ensureCopied();
        }
        
        public void sync(Runnable task)
        {
            this.common.sync(task);
        }
        
        public void setCancelled(boolean cancelled)
        {
            this.common.setCancelled(cancelled);
        }
        public boolean isCancelled()
        {
            return this.common.isCancelled();
        }
    }
}
