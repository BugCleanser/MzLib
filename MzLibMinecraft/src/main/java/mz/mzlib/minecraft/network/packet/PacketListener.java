package mz.mzlib.minecraft.network.packet;

import mz.mzlib.util.wrapper.WrapperFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public class PacketListener<T extends Packet>
{
    public final WrapperFactory<T> factory;
    public float priority;
    public Consumer<PacketEvent.Specialized<T>> handler;
    
    public PacketListener(WrapperFactory<T> factory, float priority, Consumer<PacketEvent.Specialized<T>> handler)
    {
        this.factory = factory;
        this.priority = priority;
        this.handler = handler;
    }
    public PacketListener(WrapperFactory<T> factory, Consumer<PacketEvent.Specialized<T>> handler)
    {
        this(factory, 0.f, handler);
    }
    @Deprecated
    public PacketListener(Function<Object, T> creator, float priority, Consumer<PacketEvent.Specialized<T>> handler)
    {
        this(new WrapperFactory<>(creator), priority, handler);
    }
    @Deprecated
    public PacketListener(Function<Object, T> creator, Consumer<PacketEvent.Specialized<T>> handler)
    {
        this(creator, 0.f, handler);
    }
    
    public void call(PacketEvent event)
    {
        this.handler.accept(event.specialize(this.factory));
    }
}
