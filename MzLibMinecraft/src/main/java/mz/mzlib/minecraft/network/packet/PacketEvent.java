package mz.mzlib.minecraft.network.packet;

import io.netty.channel.Channel;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.util.Option;
import mz.mzlib.util.TaskList;
import mz.mzlib.util.wrapper.WrapperFactory;

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
    
    public <T extends Packet> T getPacket(WrapperFactory<T> factory)
    {
        return this.packet.castTo(factory);
    }
    @Deprecated
    public <T extends Packet> T getPacket(Function<Object, T> creator)
    {
        return this.getPacket(new WrapperFactory<>(creator));
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
    
    public <T extends Packet> Specialized<T> specialize(WrapperFactory<T> factory)
    {
        return new Specialized<>(this, factory);
    }
    @Deprecated
    public <T extends Packet> Specialized<T> specialize(Function<Object, T> creator)
    {
        return new Specialized<>(this, creator);
    }
    
    public static class Specialized<T extends Packet>
    {
        public PacketEvent common;
        public WrapperFactory<T> factory;
        public Specialized(PacketEvent common, WrapperFactory<T> creator)
        {
            this.common = common;
            this.factory = creator;
        }
        @Deprecated
        public Specialized(PacketEvent common, Function<Object, T> creator)
        {
            this(common, new WrapperFactory<>(creator));
        }
        
        public Option<EntityPlayer> getPlayer()
        {
            return this.common.getPlayer();
        }
        
        public T getPacket()
        {
            return this.common.getPacket(this.factory);
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
