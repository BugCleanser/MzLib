package mz.mzlib.minecraft.network.packet;

import io.netty.channel.Channel;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.util.TaskList;

import java.util.function.Function;

public class PacketEvent
{
    public Channel channel;
    public EntityPlayer player;
    public Packet packet;
    public boolean isCancelled = false;
    public PacketEvent(Channel channel, EntityPlayer player, Packet packet)
    {
        this.channel = channel;
        this.player = player;
        this.packet = packet;
    }
    
    public EntityPlayer getPlayer()
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
        return new Specialized<>(packetCreator);
    }
    
    public class Specialized<T extends Packet>
    {
        public Function<Object, T> packetCreator;
        public Specialized(Function<Object, T> packetCreator)
        {
            this.packetCreator = packetCreator;
        }
        
        public EntityPlayer getPlayer()
        {
            return PacketEvent.this.getPlayer();
        }
        
        public T getPacket()
        {
            return PacketEvent.this.getPacket(this.packetCreator);
        }
        
        public void ensureCopied()
        {
            PacketEvent.this.ensureCopied();
        }
        
        public void sync(Runnable task)
        {
            PacketEvent.this.sync(task);
        }
        
        public void setCancelled(boolean cancelled)
        {
            PacketEvent.this.setCancelled(cancelled);
        }
        public boolean isCancelled()
        {
            return PacketEvent.this.isCancelled();
        }
    }
}
